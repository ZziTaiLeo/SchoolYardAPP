package com.hd.app.recommend;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hd.app.R;
import com.hd.app.adapter.CardFragmentPagerAdapterRecommend;
import com.hd.app.adapter.CardPagerAdapterRecommend;
import com.hd.app.base.BaseActivity;
import com.hd.app.bean.CardItem_Recommend;

import com.hd.app.bean.getDishBean;
import com.hd.app.navigation.NavigationActivity;
import com.hd.app.util.ShadowTransformerRecommend;

import org.json.JSONObject;

import static com.hd.app.recommend.QuestionActivity.dpToPixels;

public class RecommendResultActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private ViewPager mViewPager;
    private CardPagerAdapterRecommend mCardAdapterRecommend;
    private ShadowTransformerRecommend mCardShadowTransformerRecommend;
    private CardFragmentPagerAdapterRecommend cardFragmentPagerAdapterRecommend;
    private ShadowTransformerRecommend shadowTransformerRecommend;
    private String TAG =  "RecommendResult";
    private getDishBean mDishBean =new getDishBean();
    private boolean mShowingFragments = false;
    /* 初始值可删 */
    private String[] restaurantArray = new String [5];
    /* 初始值可删 */
    private String[] dishesArray = new String [5];
    /* 初始值可删 */
    private String[] canteensArray = new String [5];
    /* 初始值可删 */
    private String[] dishNumArray = {"dish1", "dish2", "dish3", "dish4", "dish5"};
    private double[] longitudeArray = new double [5];
    private double[] latitudeArray = new double [5];
    /* 初始值可删 */
    /**
     *  初始值可删 */
    private String demoDishes;
    private Button btGo;
    private Button btNextRest;
    private int httpCode;
    private String forwordMessage;
    private int dishNum;
    private String setDemoRestaurant(String restaurant, String canteenName) {
        return "向你推荐"+"\n"+"\n"+"\"" + canteenName + "\"" + restaurant+"\n" ;
    }
    private String setDemoDishes(String dishs) {
        return this.demoDishes = "《" + dishs + "》";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomend_result);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCardAdapterRecommend = new CardPagerAdapterRecommend();
        /**
         读取上个intent的信息
         */
        forwordMessage =getIntent().getStringExtra("DishBeanJson");
        dishNum = getIntent().getIntExtra("dishNum", 3);
        Log.d(TAG,forwordMessage);
        Gson gson = new Gson();
        mDishBean = gson.fromJson(forwordMessage, getDishBean.class);
        parseJSONWithJSONObject(forwordMessage);
        for(int i =0;i<5;i++){
            System.out.println("Restname : "+restaurantArray[i]);
        }

        for (int i = 0; i < dishNum; i++) {
            String number = Integer.toString(i + 1);
            mCardAdapterRecommend.addCardItem(new CardItem_Recommend(setDemoRestaurant(restaurantArray[i], canteensArray[i]), setDemoDishes(dishesArray[i]), number));
        }
        cardFragmentPagerAdapterRecommend = new CardFragmentPagerAdapterRecommend(getSupportFragmentManager(),
                dpToPixels(2, this));
        mCardShadowTransformerRecommend = new ShadowTransformerRecommend(mViewPager, mCardAdapterRecommend);
        shadowTransformerRecommend = new ShadowTransformerRecommend(mViewPager, cardFragmentPagerAdapterRecommend);
        mViewPager.setAdapter(mCardAdapterRecommend);
        mViewPager.setPageTransformer(false, mCardShadowTransformerRecommend);
        mViewPager.setOffscreenPageLimit(2);
        btGo = (Button) findViewById(R.id.take_me_go);
        btNextRest = (Button) findViewById(R.id.next_restaurant);
        btNextRest.setOnClickListener(this);
        btGo.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.take_me_go: {
                String item = Integer.toString(mViewPager.getCurrentItem());
                int i = Integer.valueOf(item);
                /**
                 * 需要更改活动名
                 */
                Intent intent = new Intent(RecommendResultActivity.this, NavigationActivity.class);
                intent.putExtra("action","1");
                intent.putExtra("latitude",latitudeArray[i]);
                intent.putExtra("logitude",longitudeArray[i]);
                intent.putExtra("spotName",canteensArray[i]);
                startActivity(intent);
                break;
            }
            case R.id.next_restaurant: {

                int number =5;
                Log.d("NUMBER",Integer.toString(dishNum));
                int currentItem = mViewPager.getCurrentItem();
                currentItem = currentItem + 1;
                if (currentItem > number - 1) {
                    currentItem = currentItem % number;
                    Log.d("currentNumber",Integer.toString(currentItem));
                }
                mViewPager.setCurrentItem(currentItem);
            }
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        mCardShadowTransformerRecommend.enableScaling(b);
        shadowTransformerRecommend.enableScaling(b);
    }
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            {
                JSONObject jsonObject = new JSONObject(jsonData);
                for (int i = 0; i < dishNum; i++) {
                    String message;
                    message = jsonObject.getString(dishNumArray[i]);
                    JSONObject messageDish = new JSONObject(message);
                    restaurantArray[i] = messageDish.getString("resName");
                    dishesArray[i] = messageDish.getString("name");
                    canteensArray[i] = messageDish.getString("canteen");
                    latitudeArray[i] = messageDish.getDouble("latitude");
                    longitudeArray[i] = messageDish.getDouble("longitude");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
