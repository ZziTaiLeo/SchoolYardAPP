package com.hd.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bm.library.PhotoView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Tools.ActivityCollector;
import adapter.FloorListAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import module.Building;
import module.Spot;
import module.User;

public class MainActivity extends AppCompatActivity {
    public static MapView mMapView = null;
    public static BaiduMap mBaiduMap;

    private TextView userNumText;
    private SharedPreferences pref;
    private String userAccount;
    private Button routeOpen;
    private Button personList;
    private EditText searchContent;
    private Button search;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private  Button locationButton;
    private Button follow_icon;
    private Button normal_icon;
    private CheckBox spotOpenCheck;
    private CheckBox indoorOpenCheck;
    /**
     * 下面三个元素是室内图功能的按键
     */
    private CardView cardView;
    private Button closeFloorCard;
    private TextView buildingNameText;
    public static PhotoView floorImg;
    //


    private ProgressBar markerInitProgress;
    public LocationClient mLocationClient;
    private List<String> permissionList = new ArrayList<>();//申请的静态权限表

    public static double lastX = 0.0;
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /**
     * 当前的精度
     */

    private float mCurrentAccracy;
    private int mCurrentDirection = 0;

    public static BDLocation nlocation = null;

    private SensorManager sensorManager;

    private Sensor accelerometerSensor;

    private Sensor magneticFieldSensor;

    private float[] accelerometerValues = new float[3];

    private float[] magneticValues = new float[3];

    //旋转矩阵,用来保存磁场和加速度的数据
    private float[] r = new float[9];

    //模拟方向传感器的数据(原始数据为弧度)
    private float[] values = new float[3];
    private User user;
    private List<Spot> spotList = new ArrayList<>();//景点信息列表
    private List<Marker> spotMarkerList = new ArrayList<>();
    private List<Marker> indoorMarkerList = new ArrayList<>();

    private MyLocationConfiguration myLocationConfiguration;

    List<Building> buildingList = new ArrayList<>();

    private long mExitTime = System.currentTimeMillis();//mExitTime为系统时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ActivityCollector.addActivity(MainActivity.this);
        Intent intent = getIntent();
        user = (User) getIntent().getSerializableExtra("user_information");
        Log.d("用户信息传送",user.getAccount());
        initPermission();//静态申请手机权限
        getSensorManager();;//初始化传感器
        init();//初始化控件
        initMap();//初始化地图
        //initMapMarker();//初始化景点标记
        setListener();
    }


    /**
     * 申请手机权限
     */

    private void initPermission() {
        {
            String[] permissions = {
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };

            ArrayList<String> toApplyList = new ArrayList<String>();

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                    toApplyList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }
            String[] tmpList = new String[toApplyList.size()];
            if (!toApplyList.isEmpty()) {
                ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须要同意以上所有权限才能正常使用app", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }

                    }
                } else {
                    Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
            default:
        }
    }


//    private void requestAuthority()
//    {
//        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);//申请手机定位权限
//        }
//        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);//读取系统信息，包含系统版本等信息，用作统计
//        }
//        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//读写内存
//        }
//        if(!permissionList.isEmpty())
//        {
//            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
//        }
//    }
//    //权限申请回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode,String [] permissions,int[]grantResults)
//    {
//        switch (requestCode)
//        {
//            case 1: {
//                if (grantResults.length > 0) {
//                    for (int result : grantResults) {
//                        if (result != PackageManager.PERMISSION_GRANTED) {
//                            Toast.makeText(this, "必须要同意以上所有权限才能正常使用app", Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
//                        }
//
//                    }
//                }
//                else{
//                    Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            }
//                default:
//        }
//
//    }
//

    /**
     * 初始化控件
     */
    private void init() {




        //获取地图控件引用
        markerInitProgress = (ProgressBar)findViewById(R.id.marker_init_progress);
        BaiduMapOptions options = new BaiduMapOptions();
        //初始化控件
        routeOpen = (Button)findViewById(R.id.route_icon);
        normal_icon = (Button)findViewById(R.id.icon_normal);
        follow_icon = (Button)findViewById(R.id.icon_follow);
        personList = (Button) findViewById(R.id.person_icon);
//        searchContent = (EditText) findViewById(R.id.search_text);
//        search = (Button) findViewById(R.id.search_icon);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout=navigationView.getHeaderView(0);

        userNumText=headerLayout.findViewById(R.id.user_num);
        pref = getSharedPreferences("user",Context.MODE_PRIVATE);
        userAccount = pref.getString("account","");
        userNumText.setText(userAccount);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mMapView = (MapView) findViewById(R.id.bmapView);
//        myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,true,);
        locationButton = (Button)findViewById(R.id.locotion_icon);
        spotOpenCheck = (CheckBox)findViewById(R.id.spot_open_icon);
        indoorOpenCheck = (CheckBox)findViewById(R.id.indoor_open_icon);

        cardView = (CardView)findViewById(R.id.floor_card);
        closeFloorCard = (Button)findViewById(R.id.close_floorView);
        buildingNameText = (TextView)findViewById(R.id.building_name);
        floorImg = (PhotoView)findViewById(R.id.floor_img);
        floorImg.enable();


        initBuildingList();



//        PhotoView ph = (PhotoView)findViewById(R.id.floor_img);
//        ph.enable();
//        ph.setImageResource(R.drawable.library_f1);


    }

    private void initBuildingList()
    {
        LatLng p =null;
        Integer[] temp = null;
        Building b = null;

        p = new LatLng(26.064807,119.204324);
        temp = new Integer[]{R.mipmap.library_f5,R.mipmap.library_f4,R.mipmap.library_f3,R.mipmap.library_f2,R.mipmap.library_f1,
        };
        b = new Building("图书馆",p,0,5,temp);
        buildingList.add(b);


        p = new LatLng(26.064373,119.201957);//西三
        temp = new Integer[]{R.mipmap.west_3_f5,R.mipmap.west_3_f4,R.mipmap.west_3_f3,R.mipmap.west_3_f2,R.mipmap.west_3_f1};
        b = new Building("36栋西3教学楼",p,0,5,temp);
        buildingList.add(b);



        p = new LatLng(26.064896,119.201988);//西二
        temp = new Integer[]{R.mipmap.west_2_f5,R.mipmap.west_2_f4,R.mipmap.west_2_f3,R.mipmap.west_2_f2,R.mipmap.west_2_f1};
        b = new Building("37栋西2教学楼",p,0,5,temp);
        buildingList.add(b);

        p = new LatLng(26.065302,119.202029);//西一
        temp = new Integer[]{R.mipmap.west_1_f5,R.mipmap.west_1_f4,R.mipmap.west_1_f3,R.mipmap.west_1_f2,R.mipmap.west_1_f1};
        b = new Building("38栋西1教学楼",p,0,5,temp);
        buildingList.add(b);

        p = new LatLng(26.065983,119.201944);//中楼
        temp = new Integer[]{R.mipmap.middle_f5,R.mipmap.middle_f4,R.mipmap.middle_f3,R.mipmap.middle_f2,R.mipmap.middle_f1};
        b = new Building("39栋中教学楼",p,0,5,temp);
        buildingList.add(b);



        p = new LatLng(26.066259,119.202595);//东一
        temp = new Integer[]{R.mipmap.east_1_f5,R.mipmap.east_1_f4,R.mipmap.east_1_f3,R.mipmap.east_1_f2,R.mipmap.east_1_f1};
        b = new Building("40栋东1教学楼",p,0,5,temp);
        buildingList.add(b);


        p = new LatLng(26.066953,119.203489);//东二
        temp = new Integer[]{R.mipmap.east_2_f5,R.mipmap.east_2_f4,R.mipmap.east_2_f3,R.mipmap.east_2_f2,R.mipmap.east_2_f1};
        b = new Building("41栋东2教学楼",p,0,5,temp);
        buildingList.add(b);

        p = new LatLng(26.066689,119.204279);//东三
        temp = new Integer[]{R.mipmap.east_3_f5,R.mipmap.east_3_f4,R.mipmap.east_3_f3,R.mipmap.east_3_f2,R.mipmap.east_3_f1};
        b = new Building("42栋东3教学楼",p,0,5,temp);
        buildingList.add(b);






    }



    /**
     * 初始化地图
     */

    private void initMap() {
        //可通过options设置地图状态
        BaiduMapOptions options = new BaiduMapOptions();
        MapView mapView = new MapView(this, options);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);//定位初始精度50米
        mBaiduMap = mMapView.getMap();//获取地图实例
        float zoom = mBaiduMap.getMapStatus().zoom;
       // mapView.showZoomControls(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//设置地图初始状态
        // 开启定位图层，一定不要少了这句，否则对在地图的设置、绘制定位点将无效
        mBaiduMap.setMyLocationEnabled(true); //定位初始化
        //mBaiduMap.setIndoorEnable(true);//打开室内图
        myLocationConfiguration = new MyLocationConfiguration(mCurrentMode,true,null);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        mLocationClient = new LocationClient(getApplicationContext());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//定位的时间间隔//毫秒
        option.setNeedDeviceDirect(true);
       // option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//开启高精度定位
        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        Log.d("定位测试","执行过这里");
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
        // 初始化传感器
    }




    private void initSpotList()
    {
        Spot spot = null;
        spot = new Spot("1","福州大学福友阁",26.059707,119.206875,R.mipmap.fuyouge1);
        spotList.add(spot);//福友阁

        spot = new Spot("2","图书馆",26.064811,119.204315,R.mipmap.library1);
        spotList.add(spot);//图书馆

        spot = new Spot("3","宏晖文体综合馆",26.057833,119.203511,R.mipmap.honghui1);
        spotList.add(spot);//宏晖文体综合馆

        spot = new Spot("4","青春广场",26.062551,119.198013,R.mipmap.qcgc1);
        spotList.add(spot);//青春广场

        spot = new Spot("5","素质拓展中心",26.062596,119.202128,R.mipmap.sutuo1);
        spotList.add(spot);//素拓


    }

    /**
     * 添加地图上的景点标记
     */
    private void initMapMarker() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ConnectTool connectTool = new ConnectTool();
//                    String spotInformation=connectTool.getSpotImformation(user);
//                    parseJSONWithGSON(spotInformation);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this,"服务器连接失败",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).start();
//        Bitmap b1 = ((BitmapDrawable)getResources().getDrawable(R.drawable.east_3_1)).getBitmap();
//        Bitmap b2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.east_3_2)).getBitmap();
        initSpotList();

        //spotList.add(spot2);
       for(Spot spot : spotList)
       {

           View markerView = LayoutInflater.from(this).inflate(R.layout.marker_background, null);
           CircleImageView icon = (CircleImageView) markerView.findViewById(R.id.marker_item_icon);
           //定义Maker坐标点
           Bitmap bt = BitmapFactory.decodeResource(getResources(), spot.getImgID());
           icon.setImageBitmap(bt);
           LatLng point = new LatLng(spot.getLatitude(), spot.getLongitude());
            //构建Marker图标
           BitmapDescriptor bitmap = BitmapDescriptorFactory
                   .fromView(markerView);
           Log.d("marker测试", "initMapMarker:123 ");
        //构建MarkerOption，用于在地图上添加Marker
           OverlayOptions option = new MarkerOptions()
                   .position(point) //必传参数
                   .icon(bitmap) //必传参数
        //设置平贴地图，在地图中双指下拉查看效果
                   .flat(false)
                   .perspective(true)
                   .anchor((float)0.5,(float)0.5);
        //在地图上添加Marker，并显示
           Bundle mBundle = new Bundle();
           mBundle.putString("spotname", spot.getSpotName());
           mBundle.putString("type","0");
           Marker marker =(Marker)mBaiduMap.addOverlay(option);
           marker.setExtraInfo(mBundle);
           spotMarkerList.add(marker); //将marker以id的区别加以管理
       }

    }

    /**
     * 初始化监听器
     */
    private void setListener() {

        routeOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NavigationActivity.class);
                intent.putExtra("action","0");
                startActivity(intent);
            }
        });



        personList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                
            }
        });

        navigationView.setCheckedItem(R.id.suggestion_icon);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.log_off_icon:
                    {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        ActivityCollector.finishAll();
                        startActivity(intent);
                        break;
                    }
                    case R.id.suggestion_icon:
                    {
                        Intent intent = new Intent(MainActivity.this,SuggestionActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.set_icon:
                    {
                        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nlocation!=null) {
                    LatLng ll = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(update);
                    update = MapStatusUpdateFactory.zoomTo(18f);
                    mBaiduMap.animateMapStatus(update);
                }
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //获取地图缩放级别

            }
        });
        //添加marker点击事件的处理
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {

                Bundle bundle = marker.getExtraInfo();
                if(bundle.getString("type").equals("0")) {
                    String spotName = bundle.getString("spotname");
                    Intent intent = new Intent(MainActivity.this, SpotConcreteActivity.class);
                    intent.putExtra("spotname", spotName);//用户信息传入下一个界面
                    //根据id的值向服务器请求对应的信息
                    // requestSpotConcrete(id);
                    startActivity(intent);
                }

                if(bundle.getString("type").equals("1"))
                {
//                    String poiName =mapPoi.getName(); //名称
//                    LatLng point = mapPoi.getPosition(); //坐标
                    String buildingName = bundle.getString("buildingName");
                    RecyclerView recyclerView = findViewById(R.id.floor_list_icon);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    boolean flag = false;
                    for(int i = 0 ;i<buildingList.size();i++)
                    {
                        Building b = buildingList.get(i);

                        if(b.getBuildingName().equals(buildingName)) {
                            floorImg.setImageResource(R.drawable.select_floor_back);
                            Log.d("地理位置", b.getBuildingName());
                            FloorListAdapter adapter = new FloorListAdapter(b.getFloorList());
                            recyclerView.setAdapter(adapter);
                            buildingNameText.setText(buildingName);
                            cardView.setVisibility(View.VISIBLE);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag)
                    {
                        Toast.makeText(MainActivity.this,"当前地点尚未加入室内图",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        normal_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentMode == MyLocationConfiguration.LocationMode.FOLLOWING)
                {
                    normal_icon.setBackgroundColor(getResources().getColor(R.color.appBlue));
                    follow_icon.setBackgroundColor(getResources().getColor(R.color.white));
                    mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                    myLocationConfiguration = new MyLocationConfiguration(mCurrentMode,true,null);
                    mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
                }
            }
        });
        follow_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentMode == MyLocationConfiguration.LocationMode.NORMAL)
                {
                    normal_icon.setBackgroundColor(getResources().getColor(R.color.white));
                    follow_icon.setBackgroundColor(getResources().getColor(R.color.appBlue));
                    mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                    myLocationConfiguration = new MyLocationConfiguration(mCurrentMode,true,null);
                    mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
                }

            }
        });
        spotOpenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    spotOpenCheck.setChecked(true);
                    markerInitProgress.setVisibility(View.VISIBLE);
                    initMapMarker();
                    markerInitProgress.setVisibility(View.GONE);

                }
                else
                {
                    spotOpenCheck.setChecked(false);
                    markerInitProgress.setVisibility(View.VISIBLE);
                    removeMarker();
                    markerInitProgress.setVisibility(View.GONE);

                }
            }
        });
        indoorOpenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    float zoom = mBaiduMap.getMapStatus().zoom;
                        indoorOpenCheck.setChecked(true);
                        markerInitProgress.setVisibility(View.VISIBLE);
                        initIndoorMarker();
                        markerInitProgress.setVisibility(View.GONE);


                }
                else
                {
                    indoorOpenCheck.setChecked(false);
                    markerInitProgress.setVisibility(View.VISIBLE);
                    removeIndoorMarker();
                    markerInitProgress.setVisibility(View.GONE);
                }
            }
        });

        closeFloorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.GONE);
            }
        });

//设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(mapListener);

    }

    BaiduMap.OnMapClickListener mapListener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {

            if(cardView.getVisibility()==View.VISIBLE)
            {
                cardView.setVisibility(View.GONE);
            }

        }
        /**
         * 地图内 Poi 单击事件回调函数
         *
         * @param mapPoi 点击的 poi 信息
         */
        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {

            return false;
        }



    };

    private void initIndoorMarker()
    {
        for(Building b : buildingList)
        {
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.indoor_marker);
            LatLng point = new LatLng(b.getPoint().latitude,b.getPoint().longitude);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point) //必传参数
                    .icon(bitmap) //必传参数
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(false)
                    .perspective(true)
                    .anchor((float)0.5,(float)0.5);
            //在地图上添加Marker，并显示


            Bundle mBundle = new Bundle();
            mBundle.putString("type","1");
            mBundle.putString("buildingName",b.getBuildingName());
            Marker marker =(Marker)mBaiduMap.addOverlay(option);
            marker.setExtraInfo(mBundle);
            indoorMarkerList.add(marker); //将marker以id的区别加以管理
        }
    }


    private void removeIndoorMarker()
    {
        int i = 0;
        for(i=0; i< indoorMarkerList.size(); i++)
        {
            Marker m = indoorMarkerList.get(i);
            m.remove();
        }
    }


    /**
     * 删除marker
     */
    private void removeMarker() {
        int i = 0;
        for(i=0; i< spotMarkerList.size(); i++)
        {
            Marker m = spotMarkerList.get(i);
            m.remove();
        }
    }

    /**
     * json解析
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
    }

    /**
     * 向服务器请求景点信息
     * @param id
     */

    private void requestSpotConcrete(int id) {
    }


    /**
     * 加速度传感器和磁场传感器调用（用来获取方向）
     */

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                //这里是对象,需要克隆一份,否则共用一份数据
                accelerometerValues = event.values.clone();
            }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                //这里是对象,需要克隆一份,否则共用一份数据
                magneticValues = event.values.clone();
            }
            /**
             * 填充旋转数组r
             * r:要填充的旋转数组
             * I:将磁场数据转换成实际的重力坐标中,一般可以设置为null
             * gravity:加速度传感器数据
             * geomagnetic:地磁传感器数据
             */
            SensorManager.getRotationMatrix(r,null,accelerometerValues,magneticValues);
            /**
             * R:旋转数组
             * values :模拟方向传感器的数据
             */
            sensorManager.getOrientation(r,values);
            double mXDirection =  Math.toDegrees(values[0]);
            if(Math.abs(mXDirection - lastX)>1.0) {
                mCurrentDirection = (int)mXDirection;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction( mCurrentDirection ).latitude(mCurrentLantitude)
                        .longitude(mCurrentLongitude).build();

                mBaiduMap.setMyLocationData(locData);
            }
            lastX = mXDirection;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //注册的Sensor精度发生变化时,在此处处理
        }

    };

    public void getSensorManager() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        /**
         * 传入的参数决定传感器的类型
         * Senor.TYPE_ACCELEROMETER: 加速度传感器
         * Senor.TYPE_LIGHT:光照传感器
         * Senor.TYPE_GRAVITY:重力传感器
         * SenorManager.getOrientation(); //方向传感器
         */
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        if(sensorManager != null){
            //一般在Resume方法中注册
            /**
             * 第三个参数决定传感器信息更新速度
             * SensorManager.SENSOR_DELAY_NORMAL:一般
             * SENSOR_DELAY_FASTEST:最快
             * SENSOR_DELAY_GAME:比较快,适合游戏
             * SENSOR_DELAY_UI:慢
             */
            sensorManager.registerListener(listener,accelerometerSensor,SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(listener,magneticFieldSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
        if(sensorManager != null){
            //解除注册
            sensorManager.unregisterListener(listener,accelerometerSensor);
            sensorManager.unregisterListener(listener,magneticFieldSensor);
        }

    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        ActivityCollector.removeActivity(this);//从活动栈中删除活动
    }




    /**
     * 定位监听器类
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        private boolean isFirstLocate = true;
        //定位模式
        //是否是第一次定位
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null){
                return;
            }
            //mapView 销毁后不在处理新接收的位置
            mCurrentAccracy = location.getRadius();
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            nlocation = location;
            if (location.getLocType()==BDLocation.TypeGpsLocation||location.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(location);
                return;
            }

        }
        private void navigateTo(BDLocation location)
        {
            if(isFirstLocate)
            {
                LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomTo(18f);
                mBaiduMap.animateMapStatus(update);
                isFirstLocate = false;
            }

        }
    }

    @Override
    public void onBackPressed() {//判断用户是否连续点击两次返回按键
        if(System.currentTimeMillis() - mExitTime < 800) {  //两次连点间隔小于0.8秒
            ActivityCollector.finishAll();  //关闭所有活动，退出应用
            android.os.Process.killProcess(android.os.Process.myPid());//关闭进程（彻底关闭应用）
        }
        else{
            Toast.makeText(MainActivity.this,"再按一次返回键退出应用",Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();   //这里赋值最关键，别忘记
        }
    }
}
