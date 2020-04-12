package com.hd.app.recommend;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.hd.app.R;
import com.hd.app.adapter.CardFragmentPagerAdapterRecommend;
import com.hd.app.adapter.CardPagerAdapterRecommend;
import com.hd.app.base.BaseActivity;
import com.hd.app.bean.CardItem_Recommend;

import com.hd.app.navigation.NavigationActivity;
import com.hd.app.util.ShadowTransformerRecommend;

import static com.hd.app.recommend.QuestionActivity.dpToPixels;

public class RecommendResultActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    private ViewPager mViewPager;
    private CardPagerAdapterRecommend mCardAdapterRecommend;
    private ShadowTransformerRecommend mCardShadowTransformerRecommend;
    private CardFragmentPagerAdapterRecommend cardFragmentPagerAdapterRecommend;
    private ShadowTransformerRecommend shadowTransformerRecommend;
    private boolean mShowingFragments = false;
    /* 初始值可删 */
    private String[] restaurantArray = {"密密麻麻","一点点","卡路里","鱼粉","一米香"};
    /* 初始值可删 */
    private String[] dishesArray = {"鱼粉","鸡腿","鸡翅","鸡胸","鸡屁股"};
    /* 初始值可删 */
    private String[] canteensArray = {"紫荆","景园","玫瑰","玫瑰","玫瑰"};
    /* 初始值可删 */
    private String[] dishNumArray = {"dish0","dish1", "dish2", "dish3", "dish4", "dish5", "dish6", "dish7", "dish8", "dish9", "dish10"};
    /* 初始值可删 */
    private String[] dishId = {"1","2","3","4","5"};
    /**
     *  初始值可删 */
    private String[] restaurantId = {"1","2","3","4","5"};
    private String [] pictureArray = new String [5];
    private int intDisuhNumber;
    private String demoDishes;
    private String intentMessage;
    private Button btGo;
    private Button btNextRest;
    private String idRecord;
    private String userId;
    private int httpCode;
    private String token;
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
         读取文件
         */
       //SharedPreferences userMessage = getSharedPreferences("user", MODE_PRIVATE);
        //token = userMessage.getString("token", "");
       // userId = userMessage.getString("id", "");
        //SharedPreferences resultMessage = getSharedPreferences("result", MODE_PRIVATE);
       // Intent getIntent =getIntent();
        /**
         *文垚给的信息;
         */
        for (int i = 0; i < 5; i++) {
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
               // intent.putExtra("idRest",restaurantId[i]);
             //   intent.putExtra("canteenid",pictureArray[i]);
                startActivity(intent);
                //Log.d("第几页啊:", item);
                /** 接口发出**/
              //  postRequest(i);
                switch (httpCode) {
                    case 400: {
                        Toast.makeText(RecommendResultActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 401: {
                        Toast.makeText(RecommendResultActivity.this, "用户信息错误", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 402: {
                        Toast.makeText(RecommendResultActivity.this, "登录超时，请重新登陆", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 403: {
                        Toast.makeText(RecommendResultActivity.this, "评价插入失败", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;


                }
                break;
            }
            case R.id.next_restaurant: {

                int number =5;
                Log.d("NUMBER",Integer.toString(intDisuhNumber));
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




        /** parse + post 请求 这里预留，建议用新回调函数 **/
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        mCardShadowTransformerRecommend.enableScaling(b);
        shadowTransformerRecommend.enableScaling(b);
    }
}
