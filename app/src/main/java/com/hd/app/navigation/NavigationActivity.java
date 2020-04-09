package com.hd.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.bikenavi.params.BikeRouteNodeInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.baidu.mapapi.walknavi.params.WalkRouteNodeInfo;
import com.hd.app.adapter.PoiHistoryAdapter;
import com.hd.app.adapter.PoiSuggestionAdapter;
import com.hd.app.adapter.RecyclerViewDivider;
import com.hd.app.base.BaseActivity;
import com.hd.app.util.LocationManager;
import com.hd.app.util.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apiTools.BikingRouteOverlay;
import apiTools.OverlayManager;
import apiTools.WalkingRouteOverlay;
import connect.ConnectTool;
import module.RouteColloctionItem;

import static android.view.View.GONE;
import static com.hd.app.MainActivity.lastX;
import static com.hd.app.MainActivity.nlocation;


/**
 * Created by Only_ZziTai on 17/3/29.
 */

public class NavigationActivity extends BaseActivity implements OnGetGeoCoderResultListener,
        OnGetSuggestionResultListener, PoiSuggestionAdapter.OnItemClickListener
        , PoiHistoryAdapter.OnHistoryItemClickListener, BaiduMap.OnMapClickListener, OnGetRoutePlanResultListener {

    /**
     * 地理编码
     * @param savedInstanceState
     */

    private String collectCallback;
    private GeoCoder mCoder;
    private double bikeLatitude = 0;
    private double bikeLogitude = 0;

    private boolean isBegin = false;
    private boolean isEnd = false;

    private String beginName = null;
    private String endName = null;

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(NavigationActivity.this,"抱歉，未找到当前地理位置",Toast.LENGTH_SHORT).show();
                return;
            } else {
                    return;
            }
        }
    }

    private String myAddressName = null;
    //反地理编码
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {

            if(isBegin)
            {
                beginName = result.getAddress();
                isBegin = false;
            }
            if(isEnd)
            {
                endName = result.getAddress();
                isEnd =false;
            }

        }
    }



    //
    private SharedPreferences pref;//用于获取user信息
    private static final int Click_Collect_Route = 1;
    private String userAccount;


    /**
     * title
     */
    private TextView titleName;
    private Button titleRightButton;
    /**
     * 界面元素
     */
    private RelativeLayout routeMap;
    private Button locationButton;
//    private BDLocation nlocation = null;
    private ImageView returnIocn;

    private CheckBox routeCollectBox;
    private TextView costTimeText;
    private TextView distanceText;
    private LinearLayout routeInformCard;
    private Button cardHideButton;
    private Button cardShowButton;
    private Button walkButton;
    private Button bikeButton;


    private TextView chooseRouteText;
    private Button chooseRouteLeft;
    private Button chooseRouteRight;
    //骑行步行选择按钮
    private Button beginNavgation;//开始导航按钮
    private Button openRouteCollect;//打开路径收藏夹按钮


    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private MyLocationConfiguration myLocationConfiguration;
    public LocationClient mLocationClient;

    /**
     * 当前的精度
     */
    private float mCurrentAccracy;
    private int mCurrentDirection = 0;


    /**
     * 传感器
     */
    private SensorManager sensorManager;

    private Sensor accelerometerSensor;

    private Sensor magneticFieldSensor;

    private float[] accelerometerValues = new float[3];

    private float[] magneticValues = new float[3];

    //旋转矩阵,用来保存磁场和加速度的数据
    private float[] r = new float[9];

    //模拟方向传感器的数据(原始数据为弧度)
    private float[] values = new float[3];


    /**
     * 地图元素
     */
    private MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    private RouteLine routeLine;
    RoutePlanSearch mSearch = null;
    MassTransitRouteLine massroute;

    private WalkingRouteResult nowResultwalk = null;
    private BikingRouteResult nowResultbike = null;
    OverlayManager routeOverlay = null;

    boolean hasShownDialogue = false;

    private String beginLocation;
    private String endLocation;
    private List<RouteColloctionItem> routeList = new ArrayList<>();//路径链表
    private int listPoint = 0;
    private RouteLine route = null;


    /**
     * 搜索框元素
     */


    private static final String TAG = "navigationActivity";
    LinearLayout placeSearchLayout;
    RelativeLayout title_content_layout;
    EditText placeEdit;
    TextView start_place_edit, destination_edit, searchButton;
    RecyclerView recyclerviewPoi, recyclerviewPoiHistory;
    private List<SuggestionResult.SuggestionInfo> suggestionInfoList;
    private SuggestionSearch mSuggestionSearch = null;
    PoiSuggestionAdapter sugAdapter;
    boolean firstSetAdapter = true, isStartPoi = true;
    String currentAddress, start_place, destination;
    LatLng startLL, endLL, tempLL;
     PoiHistoryAdapter poiHistoryAdapter;
     PoiSearch poiSearch;
    private List<PoiInfo> poiInfo = new ArrayList<>();


    private String actionId = null;
    private double beginLatitude;
    private double beginLogitude;
    private double endLatitude;
    private double endLogitude;

    private boolean isLocationSpot = true;
    private boolean isRouteCollect = true;


    /**
     * 开始导航使用的变量
     */
    private boolean isWalk = false;
    private boolean isBike = false;
    private BikeNaviLaunchParam bikeParam;
    private WalkNaviLaunchParam walkParam;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        setStatusBar();
        init();
        initMap();
        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(this);
        getSensorManager();
        initPoiListener();

        setListener();

        Intent intent = getIntent();
        if(intent.getStringExtra("action").equals("1"))
        {
            actionId = intent.getStringExtra("action");
            endLatitude=intent.getDoubleExtra("latitude",0.0000000000);
            endLogitude=intent.getDoubleExtra("logitude",0.0000000000);
            Log.d("终点坐标",String.valueOf(endLatitude));
            if(intent.getStringExtra("destinationName")!=null)
            {
                destination_edit.setText(intent.getStringExtra("destinationName"));
            }
            else {
                destination_edit.setText(intent.getStringExtra("spotName"));
            }

            isBegin = true;
            LatLng p = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());
            ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(p);
            mCoder.reverseGeoCode(options);
            walkingRoutePlan("我的位置","景点");
            routeMap.setVisibility(View.VISIBLE);
            routeInformCard.setVisibility(View.VISIBLE);
        }
        if(intent.getStringExtra("action").equals("2"))
        {
            //从路径收藏夹打开路径规划


            actionId = intent.getStringExtra("action");
            beginLatitude = intent.getDoubleExtra("beginLatitude",0.0000000000);
            Log.d("起点纬度",String.valueOf(beginLatitude));
            beginLogitude = intent.getDoubleExtra("beginLogitude",0.0000000000);
            endLatitude = intent.getDoubleExtra("endLatitude",0.0000000000);
            endLogitude = intent.getDoubleExtra("endLogitude",0.0000000000);
            String mode = intent.getStringExtra("mode");
            String cbegin = intent.getStringExtra("beginName");
            String cend = intent.getStringExtra("endName");
            start_place_edit.setText(cbegin);
            destination_edit.setText(cend);
            switch (mode)
            {
                case "步行":
                {
                    walkingRoutePlan(cbegin,cend);
                    routeMap.setVisibility(View.VISIBLE);
                    routeInformCard.setVisibility(View.VISIBLE);
                    break;
                }
                case "骑行":
                {
                    bikingRoutePlan(cbegin,cend);
                    routeMap.setVisibility(View.VISIBLE);
                    routeInformCard.setVisibility(View.VISIBLE);
                    break;
                }
                default:
                    break;
            }

        }
    }
    private void setListener() {
        openRouteCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this,BookmarksActivity.class);
                startActivity(intent);
            }
        });
        returnIocn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cardHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeInformCard.setVisibility(GONE);
                cardShowButton.setVisibility(View.VISIBLE);
            }
        });
        cardShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeInformCard.setVisibility(View.VISIBLE);
                cardShowButton.setVisibility(GONE);
            }
        });
        walkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                walkingRoutePlan(beginLocation,endLocation);
            }
        });
        bikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bikingRoutePlan(beginLocation, endLocation);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocationSpot = false;
                isRouteCollect = false;
                beginLocation = start_place_edit.getText().toString().trim();
                endLocation = destination_edit.getText().toString().trim();
                if(beginLocation.isEmpty()||endLocation.isEmpty()) {
                    Toast.makeText(NavigationActivity.this, "起点和终点不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(beginLocation.equals("我的位置"))
                {
                    isBegin = true;
//                    mCoder.geocode(new GeoCodeOption().city("福州").address(endLocation));
                    LatLng p = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());
                    ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(p);
                    mCoder.reverseGeoCode(options);
//                    beginName = myAddressName;
//                    myAddressName = null;
                }
                if(endLocation.equals("我的位置"))
                {
                    isEnd=true;
//                    mCoder.geocode(new GeoCodeOption().city("福州").address(beginLocation));
                    LatLng p = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());
                    ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(p);
                    mCoder.reverseGeoCode(options);
//                    endName = myAddressName;
//                    myAddressName = null;
                }
                    routeMap.setVisibility(View.VISIBLE);
//                    walkButton.setBackgroundResource(R.drawable.route_mode_walk_select);
//                    walkButton.setTextColor(getResources().getColor(R.color.white));
//                    bikeButton.setBackgroundResource(R.drawable.route_mode_bike_notselect);
//                    bikeButton.setTextColor(getResources().getColor(R.color.appBlue));
                    walkingRoutePlan(beginLocation, endLocation);
                    routeInformCard.setVisibility(View.VISIBLE);
            }
        });
        chooseRouteLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPoint > 0) {
                    listPoint--;
                    chooseRouteText.setText("路线" + String.valueOf(listPoint + 1));
                    RouteColloctionItem rt = routeList.get(listPoint);
                    distanceText.setText(String.valueOf(rt.getDistance()) + "米");
                    costTimeText.setText(String.valueOf(rt.getTakeTime() / 60) + "分钟");
                } else {
                    return;
                }
            }
        });
        chooseRouteRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPoint < routeList.size() - 1) {
                    listPoint++;
                    chooseRouteText.setText("路线" + String.valueOf(listPoint + 1));
                    RouteColloctionItem rt = routeList.get(listPoint);
                    distanceText.setText(String.valueOf(rt.getDistance()) + "米");
                    costTimeText.setText(String.valueOf(rt.getTakeTime() / 60) + "分钟");
                } else {
                    return;
                }
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nlocation != null) {
                    if (nlocation != null) {
                        LatLng ll = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                        mBaiduMap.animateMapStatus(update);
                        update = MapStatusUpdateFactory.zoomTo(18f);
                        mBaiduMap.animateMapStatus(update);
                    }
                }
            }
        });

        routeCollectBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    doCollectRoute();
                    routeCollectBox.setClickable(false);
                }

            }
        });
        beginNavgation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWalk)
                {

                    LatLng startPt = new LatLng(routeList.get(listPoint).getBeginLatitude(),routeList.get(listPoint).getBeginLogitude());
                    LatLng endPt = new LatLng(routeList.get(listPoint).getEndLatitude(),routeList.get(listPoint).getEndLogitude());
                    WalkRouteNodeInfo walkStartNode = new WalkRouteNodeInfo();
                    walkStartNode.setLocation(startPt);
                    WalkRouteNodeInfo walkEndNode = new WalkRouteNodeInfo();
                    walkEndNode.setLocation(endPt);
                    walkParam = new WalkNaviLaunchParam().startNodeInfo(walkStartNode).endNodeInfo(walkEndNode);
                    try {
                        WalkNavigateHelper.getInstance().initNaviEngine(NavigationActivity.this, new IWEngineInitListener() {
                            @Override
                            public void engineInitSuccess() {
                                Log.d(TAG, "WalkNavi engineInitSuccess");
                                routePlanWithWalkParam();
                            }

                            @Override
                            public void engineInitFail() {
                                Log.d(TAG, "WalkNavi engineInitFail");
                                WalkNavigateHelper.getInstance().unInitNaviEngine();
                            }
                        });
                    } catch (Exception e) {
                        Log.d(TAG, "startBikeNavi Exception");
                        e.printStackTrace();
                    }
                    return;
                }
                if(isBike)
                {
                    LatLng startPt = new LatLng(routeList.get(listPoint).getBeginLatitude(),routeList.get(listPoint).getBeginLogitude());
                    LatLng endPt = new LatLng(routeList.get(listPoint).getEndLatitude(),routeList.get(listPoint).getEndLogitude());
                    BikeRouteNodeInfo bikeStartNode = new BikeRouteNodeInfo();
                    bikeStartNode.setLocation(startPt);
                    BikeRouteNodeInfo bikeEndNode = new BikeRouteNodeInfo();
                    bikeEndNode.setLocation(endPt);
                    bikeParam = new BikeNaviLaunchParam().startNodeInfo(bikeStartNode).endNodeInfo(bikeEndNode);
                    try {
                        BikeNavigateHelper.getInstance().initNaviEngine(NavigationActivity.this, new IBEngineInitListener() {
                            @Override
                            public void engineInitSuccess() {
                                Log.d(TAG, "BikeNavi engineInitSuccess");
                                routePlanWithBikeParam();
                            }

                            @Override
                            public void engineInitFail() {
                                Log.d(TAG, "BikeNavi engineInitFail");
                                BikeNavigateHelper.getInstance().unInitNaviEngine();
                            }
                        });
                    } catch (Exception e) {
                        Log.d(TAG, "startBikeNavi Exception");
                        e.printStackTrace();
                    }
                    return;
                }
            }
        });
    }


    /**
     * 发起骑行导航算路
     */
    private void routePlanWithBikeParam() {
        BikeNavigateHelper.getInstance().routePlanWithRouteNode(bikeParam, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "BikeNavi onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d(TAG, "BikeNavi onRoutePlanSuccess");
                Intent intent = new Intent();
                intent.setClass(NavigationActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d(TAG, "BikeNavi onRoutePlanFail");
            }

        });
    }

    /**
     * 发起步行导航算路
     */
    private void routePlanWithWalkParam() {
        WalkNavigateHelper.getInstance().routePlanWithRouteNode(walkParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d(TAG, "WalkNavi onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {

                Log.d(TAG, "onRoutePlanSuccess");

                Intent intent = new Intent();
                intent.setClass(NavigationActivity.this, WNaviGuideActivity.class);
                startActivity(intent);

            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError error) {
                Log.d(TAG, "WalkNavi onRoutePlanFail");
            }

        });
    }






    /**
     * 收藏路径，必须开线程
     */

    private void doCollectRoute()
    {
        RouteColloctionItem rt = routeList.get(listPoint);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectTool connectTool = new ConnectTool();//建立连接
                    String temp=connectTool.routeCollectRequest(rt);
                    Log.d("登录标识", temp);
                    JSONObject jsonObject = new JSONObject(temp);
                    collectCallback = jsonObject.getString("msg");

                    Message message = new Message();
                    message.what = Click_Collect_Route;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 开子线程执行网络操作
     */
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Click_Collect_Route: {
                    if(collectCallback.equals("success"))
                        Toast.makeText(NavigationActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Toast.makeText(NavigationActivity.this,"收藏失败,网络错误或者该路径已收藏",Toast.LENGTH_SHORT).show();
                    }

                }
                default:
                    break;
            }
        }
    };



    private void init()
    {


        pref = getSharedPreferences("user",Context.MODE_PRIVATE);
        userAccount = pref.getString("account","");


        returnIocn = (ImageView) findViewById(R.id.menu_icon);
        routeMap = (RelativeLayout) findViewById(R.id.route_map);
        routeCollectBox = (CheckBox) findViewById(R.id.collect_route_icon);
        costTimeText = (TextView) findViewById(R.id.route_time_text);
        distanceText = (TextView) findViewById(R.id.route_length_text);
        routeInformCard = (LinearLayout) findViewById(R.id.route_inform_card);
        cardHideButton = (Button) findViewById(R.id.hide_routecard_icon);
        cardShowButton = (Button) findViewById(R.id.show_routecard_icon);

        walkButton = (Button) findViewById(R.id.on_walk);
        bikeButton = (Button) findViewById(R.id.on_bike);

        mMapView = (MapView) findViewById(R.id.bmapRView);

        locationButton = (Button) findViewById(R.id.locotion_icon2);

        chooseRouteText = (TextView) findViewById(R.id.choose_route_text);
        chooseRouteLeft = (Button) findViewById(R.id.route_left);
        chooseRouteRight = (Button) findViewById(R.id.route_right);

        //searchButton = (TextView)findViewById(R.id.book_bt);
        currentAddress = LocationManager.getInstance().getAddress();
        placeSearchLayout = findViewById(R.id.place_search_layout);
        title_content_layout = (RelativeLayout) findViewById(R.id.title_content_layout);
        start_place_edit = (TextView) findViewById(R.id.start_place_edit);
        destination_edit = (TextView) findViewById(R.id.destination_edit);
        searchButton = (TextView) findViewById(R.id.book_bt);
        placeEdit = (EditText) findViewById(R.id.place_edit);
        /**
         * recycle 初始化
         */
        recyclerviewPoi = findViewById(R.id.recyclerview_poi);
        recyclerviewPoi.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewPoi.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.color_c8cacc)));
        recyclerviewPoiHistory = (RecyclerView) findViewById(R.id.recyclerview_poi_history);
        recyclerviewPoiHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewPoiHistory.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1,
                ContextCompat.getColor(this, R.color.color_c8cacc)));
        poiHistoryAdapter = new PoiHistoryAdapter(NavigationActivity.this, poiInfo);
        recyclerviewPoiHistory.setAdapter(poiHistoryAdapter);
        poiHistoryAdapter.setOnClickListener(this);
        beginNavgation = (Button)findViewById(R.id.begin_guide_icon);
        openRouteCollect = (Button)findViewById(R.id.open_route_collect);
    }


    private void initMap() {
        //可通过options设置地图状态
        BaiduMapOptions options = new BaiduMapOptions();
        MapView mapView = new MapView(this, options);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(16.0f);//定位初始精度200米
        mBaiduMap = mMapView.getMap();//获取地图实例
        // 地图点击事件处理
        mBaiduMap.setOnMapClickListener(NavigationActivity.this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(NavigationActivity.this);
        float zoom = mBaiduMap.getMapStatus().zoom;
        // mapView.showZoomControls(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//设置地图初始状态
        // 开启定位图层，一定不要少了这句，否则对在地图的设置、绘制定位点将无效
        mBaiduMap.setMyLocationEnabled(true); //定位初始化
        //mBaiduMap.setIndoorEnable(true);//打开室内图
        myLocationConfiguration = new MyLocationConfiguration(mCurrentMode, true, null);
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
        Log.d("定位测试", "执行过这里");
        NavigationActivity.MyLocationListener myLocationListener = new NavigationActivity.MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
        // 初始化传感器
    }


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


    /**
     * 加速度传感器和磁场传感器调用（用来获取方向）
     */

    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //这里是对象,需要克隆一份,否则共用一份数据
                accelerometerValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
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
            SensorManager.getRotationMatrix(r, null, accelerometerValues, magneticValues);
            /**
             * R:旋转数组
             * values :模拟方向传感器的数据
             */
            sensorManager.getOrientation(r, values);
            double mXDirection = Math.toDegrees(values[0]);
            if (Math.abs(mXDirection - lastX) > 1.0) {
                mCurrentDirection = (int) mXDirection;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mCurrentAccracy)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(mCurrentLantitude)
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


    private void walkingRoutePlan(String begin, String end) {
        walkButton.setBackgroundResource(R.drawable.route_mode_walk_select);
        walkButton.setTextColor(getResources().getColor(R.color.white));
        bikeButton.setBackgroundResource(R.drawable.route_mode_bike_notselect);
        bikeButton.setTextColor(getResources().getColor(R.color.appBlue));
        isBike = false;
        isWalk =true;
        mBaiduMap.clear();
        PlanNode stNode = null;
        PlanNode enNode = null;
        if(actionId!=null&&actionId.equals("1")&&isLocationSpot)
        {
            LatLng pBegin = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());
            LatLng pEnd = new LatLng(endLatitude,endLogitude);
            stNode = PlanNode.withLocation(pBegin);
            enNode = PlanNode.withLocation(pEnd);
        }
        else if(actionId!=null&&actionId.equals("2")&&isRouteCollect)
        {
            LatLng pBegin = new LatLng(beginLatitude,beginLogitude);
            LatLng pEnd = new LatLng(endLatitude,endLogitude);
            stNode = PlanNode.withLocation(pBegin);
            enNode = PlanNode.withLocation(pEnd);
        }
        else {
            if (begin.equals("我的位置")) {
                LatLng p = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                stNode = PlanNode.withLocation(p);
            } else {
                stNode = PlanNode.withCityNameAndPlaceName("福州", begin);
                beginName = begin;
            }
            if (end.equals("我的位置")) {
                LatLng p = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                enNode = PlanNode.withLocation(p);
            } else {
                enNode = PlanNode.withCityNameAndPlaceName("福州", end);
                endName = end;
            }
        }
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode).to(enNode));
    }

    private void bikingRoutePlan(String begin,String end)
    {
        bikeButton.setBackgroundResource(R.drawable.route_mode_bike_select);
        bikeButton.setTextColor(getResources().getColor(R.color.white));
        walkButton.setBackgroundResource(R.drawable.route_mode_walk_notselect);
        walkButton.setTextColor(getResources().getColor(R.color.appBlue));
        isWalk = false;
        isBike =true;
        Log.d("骑行路线规划", "bikingRoutePlan: "+end);
        mBaiduMap.clear();
        PlanNode stNode = null;
        PlanNode enNode = null;
        if(actionId!=null&&actionId.equals("1")&&isLocationSpot)
        {
            LatLng pBegin = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());
            LatLng pEnd = new LatLng(endLatitude,endLogitude);
            stNode = PlanNode.withLocation(pBegin);
            enNode = PlanNode.withLocation(pEnd);
        }
        else if(actionId!=null&&actionId.equals("2")&&isRouteCollect)
        {
            LatLng pBegin = new LatLng(beginLatitude,beginLogitude);
            LatLng pEnd = new LatLng(endLatitude,endLogitude);
            stNode = PlanNode.withLocation(pBegin);
            enNode = PlanNode.withLocation(pEnd);
        }
        else {
            if (begin.equals("我的位置")) {
                LatLng p = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                stNode = PlanNode.withLocation(p);
                LatLng p1 = new LatLng(bikeLatitude,bikeLogitude);
                //Log.d("骑行精度", String.valueOf(bikeLatitude));
                enNode = PlanNode.withLocation(p1);

            } else {
                stNode = PlanNode.withCityNameAndPlaceName("福州", begin);
                beginName = begin;
            }
            if (end.equals("我的位置")&&enNode==null) {
                LatLng p = new LatLng(nlocation.getLatitude(), nlocation.getLongitude());
                enNode = PlanNode.withLocation(p);
               // mCoder.geocode(new GeoCodeOption().city("福州").address(begin));
                LatLng p1 = new LatLng(bikeLatitude,bikeLogitude);
                stNode = PlanNode.withLocation(p1);

                //  LatLng p1 = new LatLng(nlocation.getLatitude(),nlocation.getLongitude());

            } else if(enNode==null){
                enNode = PlanNode.withCityNameAndPlaceName("福州", end);
                endName = end;
            }
        }
        mSearch.bikingSearch((new BikingRoutePlanOption().ridingType(0))
                .from(stNode).to(enNode));
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    /**
     * 步行路线回调函数
     *
     * @param result
     */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {


        routeList.clear();

        if (null == result) {
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo();
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("检索地址存在歧义");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }

        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(NavigationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
//            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
            routeCollectBox.setClickable(true);//收藏按钮点击开放
            routeCollectBox.setChecked(false);//默认未收藏
            Log.d("aaaaaab",String.valueOf(result.getRouteLines().size()) );
            if (result.getRouteLines().size() > 1) {
                Toast.makeText(NavigationActivity.this, "找到合适步行路径" + String.valueOf(result.getRouteLines().size()) + "条", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < result.getRouteLines().size(); i++) {
                    route = result.getRouteLines().get(i);
                    Date dt = new Date();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateNowStr = sdf.format(dt);
                    double bla = route.getStarting().getLocation().latitude;
                    double blo = route.getStarting().getLocation().longitude;
                    double ela = route.getTerminal().getLocation().latitude;
                    double elo = route.getTerminal().getLocation().longitude;
                    if(beginName==null)
                    {
                        beginName = start_place_edit.getText().toString();
                    }
                    if(endName==null)
                    {
                        endName=destination_edit.getText().toString();
                    }
                    RouteColloctionItem routeItem = new RouteColloctionItem(userAccount,"步行",dateNowStr,beginName,endName,route.getDuration() / 60,route.getDistance(),bla,blo,ela,elo);
                    bikeLatitude = ela;
                    bikeLogitude = elo;
                    routeList.add(routeItem);
                    listPoint = 0;
                }
                route = result.getRouteLines().get(0);
                chooseRouteText.setText("路径1");
                costTimeText.setText(String.valueOf(route.getDuration() / 60) + "分钟");
                distanceText.setText(String.valueOf(route.getDistance()) + "米");
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else if (result.getRouteLines().size() == 1) {
                // 直接显示

                Toast.makeText(NavigationActivity.this, "找到合适步行路径1条", Toast.LENGTH_SHORT).show();
                route = result.getRouteLines().get(0);
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(dt);
                double bla = route.getStarting().getLocation().latitude;
                double blo = route.getStarting().getLocation().longitude;
                double ela = route.getTerminal().getLocation().latitude;
                double elo = route.getTerminal().getLocation().longitude;
                bikeLatitude = ela;
                bikeLogitude = elo;
                if(beginName==null)
                {
                    beginName = start_place_edit.getText().toString();
                }
                if(endName==null)
                {
                    endName=destination_edit.getText().toString();
                }
                RouteColloctionItem routeItem = new RouteColloctionItem(userAccount,"步行",dateNowStr,beginName,endName,route.getDuration() / 60,route.getDistance(),bla,blo,ela,elo);

                routeList.add(routeItem);
                Log.d("用户",routeItem.getUserAccount());
                Log.d("时间",routeItem.getTime());
                Log.d("起点", routeItem.getBeginLocation());
                Log.d("终点",routeItem.getEndLocation());
                Log.d("起点纬度", String.valueOf(bla));
                Log.d("起点经度",String.valueOf(blo));
                Log.d("终点纬度", String.valueOf(bla));
                Log.d("终点经度", String.valueOf(blo));

                chooseRouteText.setText("路径1");
                costTimeText.setText(String.valueOf(route.getDuration() / 60) + "分钟");
                distanceText.setText(String.valueOf(route.getDistance()) + "米");
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
                Log.d("步行规划", String.valueOf(route.getDuration() / 60));

            } else {
                Toast.makeText(NavigationActivity.this, "未找到合适步行路径", Toast.LENGTH_SHORT).show();
                Log.d("route result", "结果数<0");
            }

        }
    }


    /**
     * 骑行路线回调函数
     *
     * @param
     */
    @Override
    public void onGetBikingRouteResult(BikingRouteResult result) {
        routeList.clear();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(NavigationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);
            builder.setTitle("提示");
            builder.setMessage("检索地址存在歧义");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routeCollectBox.setClickable(true);//收藏按钮点击开放
            routeCollectBox.setChecked(false);//默认未收藏
//
            if (result.getRouteLines().size() > 1) {
                Toast.makeText(NavigationActivity.this, "找到合适骑行路径" + String.valueOf(result.getRouteLines().size()) + "条", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < result.getRouteLines().size(); i++) {

                    route = result.getRouteLines().get(i);
                    Date dt = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateNowStr = sdf.format(dt);
                    double bla = route.getStarting().getLocation().latitude;
                    double blo = route.getStarting().getLocation().longitude;
                    double ela = route.getTerminal().getLocation().latitude;
                    double elo = route.getTerminal().getLocation().longitude;
                    if(beginName==null)
                    {
                        beginName = start_place_edit.getText().toString();
                    }
                    if(endName==null)
                    {
                        endName=destination_edit.getText().toString();
                    }

                    RouteColloctionItem routeItem = new RouteColloctionItem(userAccount,"骑行",dateNowStr,beginName,endName,route.getDuration() / 60,route.getDistance(),bla,blo,ela,elo);
                    routeList.add(routeItem);
                    listPoint = 0;
                }
                route = result.getRouteLines().get(0);
                chooseRouteText.setText("路径1");
                costTimeText.setText(String.valueOf(route.getDuration() / 60) + "分钟");
                distanceText.setText(String.valueOf(route.getDistance()) + "米");
                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                routeOverlay = overlay;
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else if (result.getRouteLines().size() == 1) {
                Toast.makeText(NavigationActivity.this, "找到合适骑行路径1条", Toast.LENGTH_SHORT).show();
                route = result.getRouteLines().get(0);
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(dt);
                double bla = route.getStarting().getLocation().latitude;
                double blo = route.getStarting().getLocation().longitude;
                double ela = route.getTerminal().getLocation().latitude;
                double elo = route.getTerminal().getLocation().longitude;
                if(beginName==null)
                {
                    beginName = start_place_edit.getText().toString();
                }
                if(endName==null)
                {
                    endName=destination_edit.getText().toString();
                }
                RouteColloctionItem routeItem = new RouteColloctionItem(userAccount,"骑行",dateNowStr,beginName,endName,route.getDuration() / 60,route.getDistance(),bla,blo,ela,elo);
                routeList.add(routeItem);
                costTimeText.setText(String.valueOf(route.getDuration() / 60) + "分钟");
                distanceText.setText(String.valueOf(route.getDistance()) + "米");
                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                routeOverlay = overlay;
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
                Log.d("骑行规划", String.valueOf(route.getDuration() / 60));
            } else {
                Toast.makeText(NavigationActivity.this, "未找到合适骑行路径", Toast.LENGTH_SHORT).show();
                return;
            }

        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }


    public void switchPoi(View view) {
        tempLL = startLL;
        startLL = endLL;
        endLL = tempLL;
        start_place = start_place_edit.getText().toString();
        destination = destination_edit.getText().toString();
        destination_edit.setText(start_place);
        start_place_edit.setText(destination);
        if (start_place_edit.getText().toString().equals(getString(R.string.input_destination))) {
            start_place_edit.setText(getString(R.string.input_start_place));
        }

    }

    public void showInputStart(View view) {
        placeEdit.requestFocus();
        new Utils(this).showIMM();
        setStatusBarLayout();
        routeMap.setVisibility(GONE);
        title_content_layout.setVisibility(View.GONE);
        placeSearchLayout.setVisibility(View.VISIBLE);
        placeEdit.setHint(getString(R.string.input_start_place));
        isStartPoi = true;

        showHistoryPOI();
    }

    public void showInputDestination(View view) {
        placeEdit.requestFocus();
        new Utils(this).showIMM();
        setStatusBarLayout();
        routeMap.setVisibility(GONE);
        title_content_layout.setVisibility(View.GONE);
        placeSearchLayout.setVisibility(View.VISIBLE);
        placeEdit.setHint(getString(R.string.input_destination));
        isStartPoi = false;

        showHistoryPOI();
    }

    public void backFromSearchPlace(View view) {
        setStatusBar();
        new Utils(this).hideIMM();
        placeEdit.setText("");
        if (sugAdapter != null) {
            sugAdapter.changeData(null);
        }
        title_content_layout.setVisibility(View.VISIBLE);
        placeSearchLayout.setVisibility(View.GONE);
    }

    /**
     * 后退箭头的监听
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (placeSearchLayout.getVisibility() == View.VISIBLE) {
                backFromSearchPlace(placeSearchLayout);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 建议结果
     *
     * @param res
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            Log.e(TAG, "fail to get suggestions, null");
            return;
        }

        recyclerviewPoiHistory.setVisibility(View.GONE);
        suggestionInfoList = res.getAllSuggestions();
        if (firstSetAdapter) {
            String from = isStartPoi ? "start" : "detination";
            //这里适配 很关键。from标识  起点/终点
            sugAdapter = new PoiSuggestionAdapter(this, suggestionInfoList, from);
            recyclerviewPoi.setAdapter(sugAdapter);
            Log.d(TAG,"sugAdapter");
            sugAdapter.setOnClickListener(this);
            firstSetAdapter = false;
        } else {
            sugAdapter.changeData(suggestionInfoList);
        }
    }

    /**
     * 展示历史记录
     */
    private void showHistoryPOI() {
        poiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(26.055801, 119.197295)).radius(3000).keyword("福州大学教学楼"
                ));
        recyclerviewPoiHistory.setVisibility(View.VISIBLE);

        if (recyclerviewPoiHistory.getAdapter()== null) {
            Log.d(TAG, "no Adapter");
        } else {
            Log.d(TAG, "is Adapter"+poiInfo.size());
        }

    }


    @Override
    public void onItemClick(View v, int position, String flag, SuggestionResult.SuggestionInfo info) {
        if (isStartPoi) {
            start_place_edit.setText(info.key);
            //         startLL = info.;
        } else {
            destination_edit.setText(info.key);
            //        endLL = info.pt;
        }
        backFromSearchPlace(placeSearchLayout);
    }

    /**
     * 设置历史记录点击项
     *
     * @param v
     * @param position 某项位置
     * @param poiInfo  数据类型
     */
    @Override
    public void onHistoryItemClick(View v, int position, PoiInfo poiInfo) {

        if (isStartPoi) {
            // startLL = new LatLng(Double.parseDouble(poiObject.lattitude), Double.parseDouble(poiObject.longitude));
            start_place_edit.setText(poiInfo.getName());
        } else {
            // endLL = new LatLng(Double.parseDouble(poiObject.lattitude), Double.parseDouble(poiObject.longitude));
            destination_edit.setText(poiInfo.getName());
        }
        backFromSearchPlace(placeSearchLayout);
    }

    /**
     * 初始化POI监听
     */
    private void initPoiListener() {
        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        poiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                poiInfo = poiResult.getAllPoi();
                poiHistoryAdapter.changeData(poiInfo);
               Log.d(TAG,String.valueOf(poiInfo.size()));
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getApplication(), "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {// 正常返回结果的时候，此处可以获得很多相关信息
                    Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                    + poiDetailResult.getAddress(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };
        poiSearch.setOnGetPoiSearchResultListener(poiListener);

        /**;
         * 当输入关键字变化时，动态更新建议列表
         */
        placeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                Log.d("Only_ZziTai", "afterTextChanged--------------");

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                Log.d("Only_ZziTai", "beforeTextChanged--------------");
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                Log.d("Only_ZziTai", "onTextChanged--------------");


                if (cs.length() <= 0) {
                    return;
                }
                String adds = placeEdit.getText().toString().trim();
                adds = "福州大学" + adds;
                Log.d("placeEdit", adds);

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch.requestSuggestion(
                        new SuggestionSearchOption()
                                .keyword("福州大学" + cs.toString()).city("福州市")
                                .citylimit(true)
                );
            }
        });
        placeEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.i("ABC", "搜索操作执行:" + placeEdit.getText());
                if (isStartPoi) {
                    start_place_edit.setText(placeEdit.getText());

                } else {
                    destination_edit.setText(placeEdit.getText());

                }
                backFromSearchPlace(placeSearchLayout);

            }
            return false;
        });
    }


    public void onBackPressed(View view) {
        Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();

        sensorManager.unregisterListener(listener, accelerometerSensor);
        sensorManager.unregisterListener(listener, magneticFieldSensor);


    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(listener, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onDestroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mCoder.destroy();
        super.onDestroy();
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
            if (location == null || mMapView == null) {
                return;
            }
            //mapView 销毁后不在处理新接收的位置
            mCurrentAccracy = location.getRadius();
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction((int) lastX).latitude(mCurrentLantitude)
                    .longitude(mCurrentLongitude).build();
            nlocation = location;
            mBaiduMap.setMyLocationData(locData);

            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
                return;
            }

        }

        private void navigateTo(BDLocation location) {
            if (isFirstLocate) {
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);
                update = MapStatusUpdateFactory.zoomTo(18f);
                mBaiduMap.animateMapStatus(update);
                isFirstLocate = false;
            }

        }
    }


}