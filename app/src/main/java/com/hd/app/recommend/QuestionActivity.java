package com.hd.app.recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hd.app.R;
import com.hd.app.adapter.CardFragmentPagerAdapterQuestion;
import com.hd.app.adapter.CardPagerAdapterQuestion;
import com.hd.app.base.BaseActivity;
import com.hd.app.bean.CardItem_Question;
import com.hd.app.bean.getDishBean;
import com.hd.app.util.ShadowTransformerQuestion;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import apiTools.callBackAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<String> mInitQuestion = new ArrayList<>();
    private ArrayList<String> restQuestion = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();
    private Button buttonYes;
    private Button buttonNo;
    private String TAG = "QuestionActivity";
    private Button buttonGo;
    private ViewPager mViewPager;
    // private String url = "http://193.112.6.8/question_request";
    public static final int UPDATE_TEXT = 1;
    private String string;
    private CardPagerAdapterQuestion mCardAdapterQuestion;
    private ShadowTransformerQuestion mCardShadowTransformerQuestion;
    private CardFragmentPagerAdapterQuestion mFragmentCardAdapterQuestion;
    private ShadowTransformerQuestion mFragmentCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mViewPager = findViewById(R.id.viewPager);
        buttonYes = findViewById(R.id.button_yes);
        buttonNo = findViewById(R.id.button_no);
        buttonGo = findViewById(R.id.btn_go);
        buttonYes.setOnClickListener(this::onClick);
        buttonNo.setOnClickListener(this::onClick);
        buttonGo.setOnClickListener(this::onClick);
        /**
         * 设置7个问题，取三个问题
         */
        initQuestion();
        /**
         * 这里收一个经纬度
         */
        buttonGo.setVisibility(View.INVISIBLE);
        /** 这里来一个发送请求,手写数据*/
        /*
         卡片设置问题
         */
        questionList();

        mCardAdapterQuestion = new CardPagerAdapterQuestion();
        mFragmentCardAdapterQuestion = new CardFragmentPagerAdapterQuestion(getSupportFragmentManager(),
                dpToPixels(2, this));

        mCardShadowTransformerQuestion = new ShadowTransformerQuestion(mViewPager, mCardAdapterQuestion);
        mFragmentCardShadowTransformer = new ShadowTransformerQuestion(mViewPager, mFragmentCardAdapterQuestion);

        mViewPager.setPageTransformer(true, mCardShadowTransformerQuestion);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (map.size() == 3) {
                    buttonGo.setVisibility(View.VISIBLE);
                } else {
                    buttonGo.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    // 在这里可以进行UI操作
                    string = msg.getData().getString("key");
                    mCardAdapterQuestion.addCardItem(new CardItem_Question("1", msg.getData().getString("content1")));
                    mCardAdapterQuestion.addCardItem(new CardItem_Question("2", msg.getData().getString("content2")));
                    mCardAdapterQuestion.addCardItem(new CardItem_Question("3", msg.getData().getString("content3")));
                    mViewPager.setAdapter(mCardAdapterQuestion);
                    mCardShadowTransformerQuestion.enableScaling(true);
                    mFragmentCardShadowTransformer.enableScaling(true);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 写一个post函数
     ***/


//    //解析回调函数，并且展示
//    private void showResponse(final  String response) {
//        Gson gson = new Gson();
//        QuestionName data = gson.fromJson(response, QuestionName.class);
//        //提取问题ID和问题内容
//        String question_id_list = data.getQuestion_id_list();
//        String content1 = data.getContent1();
//        String content2 = data.getContent2();
//        String content3 = data.getContent3();
//
//        SharedPreferences.Editor editor = getSharedPreferences("question_answer", MODE_PRIVATE).edit();
//        editor.putString("idQuestions", question_id_list);
//        editor.apply();
//
//        Message message = new Message();
//        message.what = UPDATE_TEXT;
//        Bundle bundle = new Bundle();
//        bundle.putString("content1", content1);
//        bundle.putString("content2", content2);
//        bundle.putString("content3", content3);
//        message.setData(bundle);
//        handler.sendMessage(message); // 将Message对象发送出去
//    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_yes:{

                if (map.size() < 3) {
                    if (!map.containsKey(restQuestion.get(mViewPager.getCurrentItem()))) {
                        map.put(restQuestion.get(mViewPager.getCurrentItem()), 1);
                    }

                }
                if (mViewPager.getCurrentItem() == 2 && map.size() == 3) {
                    buttonGo.setVisibility(View.VISIBLE);
                    buttonYes.setVisibility(View.INVISIBLE);
                    buttonNo.setVisibility(View.INVISIBLE);
                }
                break;
            }

            case R.id.button_no:{
                map.put(restQuestion.get(mViewPager.getCurrentItem()), 0);
                if (map.size() < 3) {
                    if (!map.containsKey(restQuestion.get(mViewPager.getCurrentItem()))) {
                        map.put(restQuestion.get(mViewPager.getCurrentItem()), 0);
                    }

                }
                if (mViewPager.getCurrentItem() == 2 && map.size() == 3) {
                    buttonGo.setVisibility(View.VISIBLE);
                    buttonYes.setVisibility(View.INVISIBLE);
                    buttonNo.setVisibility(View.INVISIBLE);
                }
                break;
            }

            case R.id.btn_go:
                postWithParamsQestion();
                break;
            default:
                break;
        }
        if (mViewPager.getCurrentItem() < 2) {
            mViewPager.arrowScroll(2);
        }
    }

    /**
     * 写一个get函数  如果用retrofit应该就不用了吧;本地手写数据
     */
    private void questionList() {
        String content1 = transformToChinese(restQuestion.get(0));
        String content2 = transformToChinese(restQuestion.get(1));
        String content3 = transformToChinese(restQuestion.get(2));
        Message message = new Message();
        message.what = UPDATE_TEXT;
        Bundle bundle = new Bundle();
        bundle.putString("content1", content1);
        bundle.putString("content2", content2);
        bundle.putString("content3", content3);
        message.setData(bundle);
        // 将Message对象发送出去
        handler.sendMessage(message);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public void initQuestion() {
        mInitQuestion.add("spicy");
        mInitQuestion.add("oil");
        mInitQuestion.add("seafood");
        mInitQuestion.add("flour");
        mInitQuestion.add("balance");
        mInitQuestion.add("rice");
        mInitQuestion.add("noodles");
        Random random = new Random();
        int n = 3;
        int i = 0;
        int myRand = 0;
        /*
        List中存了三个随机的问题
         */
        while (i < 3) {

            if(restQuestion.contains("rice")){
                mInitQuestion.remove("noodles");
                mInitQuestion.remove("flour");
            }
            if(restQuestion.contains("noodles")){
                mInitQuestion.remove("flour");
                mInitQuestion.remove("rice");

            }
            if(restQuestion.contains("flour")){
                mInitQuestion.remove("rice");
                mInitQuestion.remove("noodles");
            }
            myRand = random.nextInt(mInitQuestion.size());
            restQuestion.add(mInitQuestion.get(myRand));
            mInitQuestion.remove(myRand);
            i++;
        }

    }

    public String transformToChinese(String content) {
        switch (content) {
            case "spicy":
                return "来点辣?";
            case "oil":
                return "浓油赤酱?";
            case "seafood":
                return "加点海味鲜香?";
            case "flour":
                return "要不米粉吧？";
            case "rice":
                return "要米饭不?";
            case "noodles":
                return "吃面?";
            case "balance":
                return "饮食均衡?";
            default:
                break;
        }
        return null;
    }


    /**
     * 请求API
     */
    public void postWithParamsQestion() {
        map.put("longitude", getIntent().getDoubleExtra("mCurrentLongitude", 26.059628));
        map.put("latitude", getIntent().getDoubleExtra("mCurrentLatitude", 119.196502));
        for (Map.Entry<String,Object>entry :map.entrySet()) {
            System.out.println("key="+entry.getKey()+ " value = "+entry.getValue());
        }
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://47.95.38.37:8088/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        callBackAPI mAPI = retrofit.create(callBackAPI.class);
        Call<getDishBean> task = mAPI.postWithParamsDishes(map);
        task.enqueue(new Callback<getDishBean>() {
            @Override
            public void onResponse(Call<getDishBean> call, Response<getDishBean> response) {
               if(response.code()== HttpURLConnection.HTTP_OK){
                   try {
                       Log.d(TAG,"ResopseBody_:"+"Success");
                       Log.d(TAG,"ResopseBody_:"+response.body().toString());
                       getDishBean mGetDishBean = response.body();
                       Intent mIntent =new Intent(QuestionActivity.this,RecommendResultActivity.class);
                       mIntent.putExtra("DishBeanJson", new Gson().toJson(mGetDishBean));
                       mIntent.putExtra("dishNum", mGetDishBean.getDishNum());
                       Log.d(TAG, "dishNum  : " + mGetDishBean.getDishNum());
                       startActivity(mIntent);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
            }

            @Override
            public void onFailure(Call<getDishBean> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
