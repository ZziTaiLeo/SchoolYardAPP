package com.hd.app.classroom;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.app.R;
import com.hd.app.adapter.FirstFloorAdapter;
import com.hd.app.adapter.MySpinnerAdapter;
import com.hd.app.adapter.SecondFloorAdapter;
import com.hd.app.adapter.ThirdFloorAdapter;
import com.hd.app.bean.ResultRoom;
import com.hd.app.bean.Room;
import com.hd.app.util.MyUtil;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiTools.callBackAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

public class ClassRoomActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<String> list;
    private View iSelectbuilding;
    private TextView tv_building; //显示当前查询的教学楼
    private MySpinnerAdapter adapter;
    private LinearLayout spinnerLayout;
    private PopupWindow popupWindow;
    private LinearLayout layout;
    private ListView listView;
    private List<Room> mRooms = new ArrayList<>();
    private int floor = 1;
    private String way;
    private String[] set_way;//查询星期几
    private int my_arg = 0;
    private String TAG = "ClassRoomActivity";
    private ResultRoom resultRoom1 = new ResultRoom();
    private ResultRoom resultRoom2 = new ResultRoom();
    private ResultRoom resultRoom3 = new ResultRoom();
    private List<ResultRoom.ResultBean> mResultBeanList = new ArrayList<>();
    private String dayOfWeek = null;
    private Map<String, Object> map = new HashMap<>();
    private List<Room> mRooms1 = new ArrayList<>();
    private List<Room> mRooms2 = new ArrayList<>();
    private List<Room> mRooms3 = new ArrayList<>();
    boolean isPostRoom1 = false;
    boolean isPostRoom2 = false;
    boolean isPostRoom3 = false;
    private int week;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        Date date = new Date();
        Log.d(TAG, "DATE IS :" + date);
        MyUtil myUtil = new MyUtil(date);
        Log.d(TAG, "WEEK IS :" + myUtil.getWeek());
        week = myUtil.getWeek();
        //输入当前周
        map.put("week", week);
        //这里dayofweek是字符串
        dayOfWeek = toDayOfWeek(myUtil.getDayOfWeek());
        Toast.makeText(ClassRoomActivity.this, "当前为第" + week + "周" + "星期" + myUtil.getDayOfWeek(), Toast.LENGTH_SHORT).show();
        map.put("dayOfWeek", dayOfWeek);
        initValue();
        postWithParamsRoom(floor, 0);

    }

    /**
     * 请求数据的接口
     */
    public void postWithParamsRoom(int floorIs, int arg) {
        switch (floorIs) {
            case 1:
                isPostRoom1 = true;
                break;
            case 2:
                isPostRoom2 = true;
                break;
            case 3:
                isPostRoom3 = true;
                break;
            default:
                break;
        }
        /**
         * 更新层数
         */
        map.put("floor", floorIs);
        Log.d(TAG, "MAP PUT FLOOR IS : = " + floorIs);
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://47.95.38.37:8088/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        callBackAPI mApi = retrofit.create(callBackAPI.class);
        Call<ResultRoom> task = mApi.postWithParamsFloors(map);
        task.enqueue(new Callback<ResultRoom>() {
            @Override
            public void onResponse(Call<ResultRoom> call, Response<ResultRoom> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    try {
                        Log.d(TAG, "Success");
                        Log.d(TAG, response.body().toString());
                        switch (floorIs) {
                            case 1:
                                resultRoom1 = response.body();
                                ResultToRoom(resultRoom1, floorIs);
                                break;
                            case 2:
                                resultRoom2 = response.body();
                                ResultToRoom(resultRoom2, floorIs);
                                break;
                            case 3:
                                resultRoom3 = response.body();
                                ResultToRoom(resultRoom3, floorIs);
                                break;
                            default:
                                break;
                        }

                        /**
                         * 转成List<room> mRooms
                         */


                        initListData(arg);
                        Log.d(TAG, "after ININTADA ROOM1 SIZE IS =" + mRooms1.size());
                        Log.d(TAG, "after ININTADA ROOM2 SIZE IS =" + mRooms2.size());
                        Log.d(TAG, "after ININTADA ROOM3 SIZE IS =" + mRooms3.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultRoom> call, Throwable t) {
                Toast.makeText(ClassRoomActivity.this, "没有连接上", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化变量
     */
    private void initValue() {
        //[1]找到控件
        lv = (ListView) findViewById(R.id.lv_classroom);

        Button iv_back = (Button) findViewById(R.id.iv_back);
        Button iv_search = (Button) findViewById(R.id.iv_search);



        //返回按钮响应
        //TODO
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //查询按钮响应事件
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询空教室

                set_way = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ClassRoomActivity.this);
                builder.setTitle("请选择所要查询的星期！");
                //    设置一个下拉的列表选择项
                builder.setItems(set_way, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //my_class = classs[which];
                        Toast.makeText(ClassRoomActivity.this, "设置成功，您选择的日期为：" + set_way[which], Toast.LENGTH_SHORT).show();
                        switch (set_way[which]) {
                            case "星期一":
                                dayOfWeek = "monday";
                                break;
                            case "星期二":
                                dayOfWeek = "tuesday";
                                break;
                            case "星期三":
                                dayOfWeek = "wednesday";
                                break;
                            case "星期四":
                                dayOfWeek = "thursday";
                                break;
                            case "星期五":
                                dayOfWeek = "friday";
                                break;
                            case "星期六":
                                dayOfWeek = "saturday";
                                break;
                            case "星期天":
                                dayOfWeek = "sunday";
                                break;
                            default:
                                break;
                        }
                        /**
                         *这里是第三个map
                         */
                        map.put("dayOfWeek", dayOfWeek);
//                        for (Map.Entry<String, Object> entry : map.entrySet()) {
//                            System.out.println("key=" + entry.getKey() + " and value = " + entry.getValue() + " and map size =  " + map.size());
//                        }
                        switch (floor){
                            case 1:ResultToRoom(resultRoom1, floor);break;
                            case 2:ResultToRoom(resultRoom2, floor);break;
                            case 3:ResultToRoom(resultRoom3, floor);break;
                            default:break;
                        }
                        Log.d(TAG, "省略了resulto room");
                        initListData(my_arg);
                    }
                });
                builder.show();
            }
        });


        //自定义标题配置
        tv_building = (TextView) findViewById(R.id.text);
        iSelectbuilding = findViewById(R.id.arrowbut);
        // 实例化一个List,添加数据
        list = new ArrayList<String>();
        list.add("公共教学楼西一");
        list.add("公共教学楼西二");
        list.add("公共教学楼西三");
        // 实例化一个适配器，list的数据作为Adapter的数据
        adapter = new MySpinnerAdapter(this, list);
        // 默认设置下拉框的标题为数据的第三个,标志为2
        tv_building.setText((CharSequence) adapter.getItem(0));
        spinnerLayout = (LinearLayout) findViewById(R.id.spinnerid);
        // 点击右侧按钮，弹出下拉框
        iSelectbuilding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showWindow(v);

            }
        });
    }


    //显示下拉选择列表
    public void showWindow(View v) {
        // 找到布局文件
        layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.mypinner_dropdown, null);
        // 实例化listView
        listView = (ListView) layout.findViewById(R.id.listView);
        // 设置listView的适配器
        listView.setAdapter(adapter);
        // 实例化一个PopuWindow对象
        popupWindow = new PopupWindow(v);
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(spinnerLayout.getWidth());
        // 高度随着内容变化
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        // 设置焦点
        popupWindow.setFocusable(true);
        // 设置所在布局
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(v, -tv_building.getWidth(), 0);
        // listView的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 设置所选的item作为下拉框的标题
                tv_building.setText(list.get(arg2));
                //设置当前选项
                my_arg = arg2;
                //实现选择响应事件
                switch (my_arg) {
                    case 0: {
                        floor = 1;
                        if (isPostRoom1 == false) {
                            postWithParamsRoom(floor, my_arg);

                        } else {
                            initListData(my_arg);
                        }
                    }
                    break;
                    case 1:
                        floor = 2;
                        if (isPostRoom2 == false) {
                            Log.d(TAG, "case 1 floor 2 and post");
                            postWithParamsRoom(floor, my_arg);
                        } else {
                            Log.d(TAG, "case 1 floor 2 and but no post");
                            initListData(my_arg);
                        }
                    case 2:
                        floor = 3;
                        if (isPostRoom3 == false) {
                            postWithParamsRoom(floor, my_arg);
                        } else {
                            initListData(my_arg);
                        }
                        break;
                    default:
                        break;
                }

                // 弹框消失
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    private void initListData(int arg2) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + " and value = " + entry.getValue() + " and map size =  " + map.size());
        }
        switch (arg2) {
            //选择的是西1
            case 0:
                lv.setAdapter(new FirstFloorAdapter(getApplicationContext(), mRooms1));
                break;

            //选择的是西1 2L
            case 1:
                lv.setAdapter(new SecondFloorAdapter(getApplicationContext(), mRooms2));
                break;
            //选择的西3
            case 2:
                lv.setAdapter(new ThirdFloorAdapter(getApplicationContext(), mRooms3));
                break;
            default:

                break;
        }

    }

    /**
     * 把接口结果拿回来，然后转换成list<room>
     *
     * @param resultRoom
     */
    public void ResultToRoom(ResultRoom resultRoom, int floorIs) {
        //取出list
        switch (floorIs) {
            case 1:
                mRooms1.clear();
                break;
            case 2:
                mRooms2.clear();
                break;
            case 3:
                mRooms3.clear();
                break;
            default:
                break;
        }
        mResultBeanList = resultRoom.getResult();
        for (ResultRoom.ResultBean resultBean : mResultBeanList) {
            Room room = new Room();
            //设置room
            room.setDoorId(resultBean.getDoorId());
            room.setCourseName(resultBean.getCourseName());
            room.setTeacherName(resultBean.getTeacherName());
            switch (dayOfWeek) {
                case "monday":
                    room.setStartTime(resultBean.getMonday());
                    break;
                case "tuesday":
                    room.setStartTime(resultBean.getTuesday());
                    break;
                case "wednesday":
                    room.setStartTime(resultBean.getWednesday());
                    break;
                case "thursday":
                    room.setStartTime(resultBean.getThursday());
                    break;
                case "friday":
                    room.setStartTime(resultBean.getFriday());
                    break;
                case "saturday":
                    room.setStartTime(resultBean.getSaturday());
                    break;
                case "sunday":
                    room.setStartTime(resultBean.getSunday());
                    break;
                default:
                    break;
            }
            //mRooms.add(room);
            switch (floorIs) {
                case 1:
                    mRooms1.add(room);
                    break;
                case 2:
                    mRooms2.add(room);
                    break;
                case 3:
                    mRooms3.add(room);
                    break;
                default:
                    break;
            }
        }
//        switch (floorIs) {
//            case 1:
//                mRooms1 = mRooms;
//                break;
//            case 2:
//                mRooms2 = mRooms;
//                break;
//            case 3:
//                mRooms3 = mRooms;
//                break;
//            default:
//                break;
//        }
        Log.d(TAG, "Transform mRooms Size is :" + String.valueOf(mRooms.size()));
        Log.d(TAG, "Transform mRooms1 Size is :" + String.valueOf(mRooms1.size()));
        Log.d(TAG, "Transform mRooms2 Size is :" + String.valueOf(mRooms2.size()));
        Log.d(TAG, "Transform mRooms3 Size is :" + String.valueOf(mRooms3.size()));

    }

    public String toDayOfWeek(int day) {
        String value = null;
        switch (day) {
            case 1:
                value = "monday";
                break;
            case 2:
                value = "tuesday";
                break;
            case 3:
                value = "wednesday";
                break;
            case 4:
                value = "thursday";
                break;
            case 5:
                value = "friday";
                break;
            case 6:
                value = "saturday";
                break;
            case 7:
                value = "sunday";
                break;
            default:
                break;


        }
        return value;

    }
}
