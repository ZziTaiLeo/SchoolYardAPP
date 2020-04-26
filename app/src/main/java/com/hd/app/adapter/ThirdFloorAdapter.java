package com.hd.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hd.app.R;
import com.hd.app.bean.Room;

import java.util.List;


/**
 * 作者：Jacky
 * 邮箱：550997728@qq.com
 * 时间：2016/3/5 13:01
 */
//创建教学楼空教室适配
public class ThirdFloorAdapter extends BaseAdapter {
          LayoutInflater inflater;
          Context context;
          List<Room> rooms=null;
          private Button b_1;
          private Button b_2;
          private Button b_3;
          private Button b_4;
          private Button b_5;
          private String room_name;
          private Room room = new Room();
    private String TAG = "ThirdFloorAdapter";


          //ArrayList<String> list;
          public ThirdFloorAdapter(Context context, List<Room> rooms) {
                    this.context = context;
                    inflater = LayoutInflater.from(context);
                    this.rooms=rooms;
          }
          @Override

          public int getCount() {
                    return 6;
          }

          @Override
          public Object getItem(int position) {
                    return null;
          }

          @Override
          public long getItemId(int position) {
                    return 0;
          }

          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
                    View view;

                    if (convertView == null) {
                              view = inflater.inflate(R.layout.activity_classroom_item, null);
                              b_1 = (Button) view.findViewById(R.id.b_01);
                              b_2 = (Button) view.findViewById(R.id.b_02);
                              b_3 = (Button) view.findViewById(R.id.b_03);
                              b_4 = (Button) view.findViewById(R.id.b_04);
                              b_5 = (Button) view.findViewById(R.id.b_05);
                    } else {
                              view = convertView;
                              b_1 = (Button) view.findViewById(R.id.b_01);
                              b_2 = (Button) view.findViewById(R.id.b_02);
                              b_3 = (Button) view.findViewById(R.id.b_03);
                              b_4 = (Button) view.findViewById(R.id.b_04);
                              b_5 = (Button) view.findViewById(R.id.b_05);
                              //初始化控件
                        /**
                         *  ContextCompat.getDrawable(context,R.drawable.classroom_false); 之后过时的方法可以这样改，再试试
                         */

                              b_1.setText("空");
                              b_1.setBackground(context.getResources().getDrawable(R.drawable.classroom_false));
                              b_1.setTextColor(context.getResources().getColor(R.color.text_false_color));
                              b_2.setText("空");
                              b_2.setBackground(context.getResources().getDrawable(R.drawable.classroom_false));
                              b_2.setTextColor(context.getResources().getColor(R.color.text_false_color));
                              b_3.setText("空");
                              b_3.setBackground(context.getResources().getDrawable(R.drawable.classroom_false));
                              b_3.setTextColor(context.getResources().getColor(R.color.text_false_color));
                              b_4.setText("空");
                              b_4.setBackground(context.getResources().getDrawable(R.drawable.classroom_false));
                              b_4.setTextColor(context.getResources().getColor(R.color.text_false_color));
                              b_5.setText("空");
                              b_5.setBackground(context.getResources().getDrawable(R.drawable.classroom_false));
                              b_5.setTextColor(context.getResources().getColor(R.color.text_false_color));
                    }

                    //设置查询到的数据 并且更新UI
                    TextView tv_roomId = (TextView) view.findViewById(R.id.tv_roomid);
                    //TODO可优化
                    System.out.println(position);
              /**
               * 这里是下拉教室号，需要调整。
               */
              if (position < 5) {
                              room_name = "30" + (position + 1) % 6;
                              //设置教室名
                              tv_roomId.setText(room_name);
                              //查询数据不为0，则更新UI
                              if(rooms!=null && !rooms.isEmpty()){
                                        //更新UI
                                        showUI();
                              }
                    } else if (position == 5) {
                              room_name="306";
                              tv_roomId.setText(room_name);
                              //查询数据不为0，则更新UI
                              if(rooms!=null && !rooms.isEmpty()){
                                        //更新UI
                                        showUI();
                              }
                    }
                    return view;
          }

    /**
     * 这里显示每个教室不同时间的小item
     */

          //初始化控件
          public void showUI(){
              Log.d(TAG, "执行到showUI");
              for (Room room : rooms) {
                  //判断对应教室
                  //如果是周 1 2 3 4 7则需要上晚自习，教室被占
                  if (room_name.equals(String.valueOf(room.getDoorId()))) {
                      //判断第几节课
                      switch (room.getStartTime()) {
                          case 1:
                              //判断上几节课
                              //更改显示字符
                              b_1.setText(room.getCourseName());

                              //设置背景颜色
                              b_1.setBackground(context.getResources().getDrawable(R.drawable.classroom_true));
                              //设置文字颜色为白色
                              b_1.setTextColor(context.getResources().getColor(R.color.text_ture_color));
                              break;

                          case 3:
                              //判断上几节课
                              //更改显示字符
                              b_2.setText(room.getCourseName());
                              //设置背景颜色
                              b_2.setBackground(context.getResources().getDrawable(R.drawable.classroom_true));
                              //设置文字颜色为白色
                              b_2.setTextColor(context.getResources().getColor(R.color.text_ture_color));
                              break;

                          case 5: {
                              //判断上几节课
                              //更改显示字符
                              b_3.setText(room.getCourseName());
                              //设置背景颜色
                              b_3.setBackground(context.getResources().getDrawable(R.drawable.classroom_true));
                              //设置文字颜色为白色
                              b_3.setTextColor(context.getResources().getColor(R.color.text_ture_color));
                              break;
                          }
                          case 7: {
                              //判断上几节课
                              //更改显示字符
                              b_4.setText(room.getCourseName());
                              //设置背景颜色
                              b_4.setBackground(context.getResources().getDrawable(R.drawable.classroom_true));
                              //设置文字颜色为白色
                              b_4.setTextColor(context.getResources().getColor(R.color.text_ture_color));

                              break;
                          }
                          case 9: {
                              //判断上几节课
                              //更改显示字符
                              b_5.setText(room.getCourseName());
                              //设置背景颜色
                              b_5.setBackground(context.getResources().getDrawable(R.drawable.classroom_true));
                              //设置文字颜色为白色
                              b_5.setTextColor(context.getResources().getColor(R.color.text_ture_color));

                              break;
                          }
                          default:
                              break;
                      }

                  }
              }
          }

}
