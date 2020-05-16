package com.hd.app.sport;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.track.OnTrackListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.hd.app.R;
import com.hd.app.util.Constants;

import java.util.ArrayList;

public class SportActivity extends AppCompatActivity implements View.OnClickListener {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    public static BDLocation nlocation = null;
    private LocationClient mLocationClient = null;
    private MyLocationConfiguration myLocationConfiguration = null;
    private float mCurrentAccracy;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private String TAG = "SportActivity";
    private long serviceId = 220465;
    private String entityName = "SchoolYard";
    boolean isNeedObjectStorage = false;
    private Trace mTrace = null;
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    private LBSTraceClient mTraceClient = null;

    /**
     * 按钮组件更新
     */
    private Button bt_start_service;
    private Button bt_start_gather;
    private Button bt_stop_service;
    private Button bt_stop_gather;
    /**
     * 轨迹服务监听器
     */
    private OnTraceListener mTraceListener = null;

    /**
     * 轨迹监听器(用于接收纠偏后实时位置回调)
     */
    private OnTrackListener trackListener = null;

    /**
     * Entity监听器(用于接收实时定位回调)
     */
    private OnEntityListener entityListener = null;

    /**
     * 实时定位任务
     */

    private boolean isStartService = false;
    private boolean isStartGather = false;

    private int notifyId = 0;

    /**
     * 打包周期
     */
    public int packInterval = Constants.DEFAULT_PACK_INTERVAL;
    private int gatherInterval = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_sport);
        initPermission();
        //可通过options设置地图状态
        mTrace = new Trace(serviceId, entityName, isNeedObjectStorage);
        mTraceClient = new LBSTraceClient(getApplicationContext());
        mTraceClient.setInterval(gatherInterval, packInterval);
        initView();
        initmap();
        initListener();
    }

        //鹰眼轨迹


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_service:
                if (isStartService == false) {
                    Log.d(TAG,"开启服务");
                    mTraceClient.startTrace(mTrace, mTraceListener);

                    isStartService = true;
                } else {
                    Log.d(TAG,"已经开启服务");
                    Toast.makeText(SportActivity.this, "已经开启服务", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_start_gather:
                if (isStartGather == false) {
                    mTraceClient.startGather(mTraceListener);
                    isStartGather = true;
                } else {
                    Toast.makeText(SportActivity.this, "已经开启采集", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_stop_gather:
                if (isStartGather) {
                    mTraceClient.stopGather(mTraceListener);
                    isStartGather = false;
                } else {
                    Toast.makeText(SportActivity.this, "已经停止采集", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_stop_service:
                if (isStartService) {
                    mTraceClient.stopTrace(mTrace, mTraceListener);
                    isStartService = false;
                } else {
                    Toast.makeText(SportActivity.this, "已经停止服务", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }

    }

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
            mCurrentLatitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            nlocation = location;
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

    public void initmap() {
        BaiduMapOptions options = new BaiduMapOptions();
        mMapView = (MapView) findViewById(R.id.bmapView);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);//定位初始精度50米
        mBaiduMap = mMapView.getMap();//获取地图实例
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
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
        // 初始化传感器
    }

    private void initListener() {
        mTraceListener = new OnTraceListener() {
            @Override
            public void onBindServiceCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onBindServiceCallback is "+s);
            }

            @Override
            public void onStartTraceCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onStartTraceCallback "+s);
            }

            @Override
            public void onStopTraceCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onStopTraceCallback "+s);
            }

            @Override
            public void onStartGatherCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onStartGatherCallback "+s);
            }

            @Override
            public void onStopGatherCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onStopGatherCallback "+s);
            }

            @Override
            public void onPushCallback(byte b, PushMessage pushMessage) {
                Toast.makeText(SportActivity.this, pushMessage.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInitBOSCallback(int i, String s) {
                Toast.makeText(SportActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"onInitBOSCallback "+s);
            }
        };
    }

    public void initView() {
        bt_start_gather = (Button) findViewById(R.id.bt_start_gather);
        bt_start_service = (Button) findViewById(R.id.bt_start_service);
        bt_stop_gather = (Button) findViewById(R.id.bt_stop_gather);
        bt_stop_service = (Button) findViewById(R.id.bt_stop_service);

    }

}
