package com.hd.app.recommend;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.hd.app.R;
import com.hd.app.adapter.CardFragmentPagerAdapterQuestion;
import com.hd.app.adapter.CardPagerAdapterQuestion;
import com.hd.app.base.BaseActivity;
import com.hd.app.bean.CardItem_Question;
import com.hd.app.bean.QuestionName;
import com.hd.app.bean.Recommend;
import com.hd.app.util.ShadowTransformerQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends BaseActivity  implements  View.OnClickListener{

    private Button buttonYes;
    private Button buttonNo;
    private Button buttonGo;
    private ViewPager mViewPager;
   // private String url = "http://193.112.6.8/question_request";
    private int http_code;
    public static final int UPDATE_TEXT = 1;
    private String string;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private String result;
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

        buttonGo.setVisibility(View.INVISIBLE);
        /** 这里来一个发送请求,手写数据*/
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
                if(list.size() == 3) {
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
    /** 写一个post函数
     *
     ***/




    //解析回调函数，并且展示
    private void showResponse(final  String response) {
        Gson gson = new Gson();
        QuestionName data = gson.fromJson(response, QuestionName.class);
        //提取问题ID和问题内容
        String question_id_list = data.getQuestion_id_list();
        String content1 = data.getContent1();
        String content2 = data.getContent2();
        String content3 = data.getContent3();

        SharedPreferences.Editor editor = getSharedPreferences("question_answer", MODE_PRIVATE).edit();
        editor.putString("idQuestions", question_id_list);
        editor.apply();

        Message message = new Message();
        message.what = UPDATE_TEXT;
        Bundle bundle = new Bundle();
        bundle.putString("content1", content1);
        bundle.putString("content2", content2);
        bundle.putString("content3", content3);
        message.setData(bundle);
        handler.sendMessage(message); // 将Message对象发送出去
    }
    @Override
    public void onClick(View view) {
        Map<String,String> map = new HashMap<>();
        SharedPreferences.Editor editor = getSharedPreferences("question_answer", MODE_PRIVATE).edit();
        editor.putString("ans", list.toString());
        editor.apply();
        switch (view.getId()) {
            case R.id.button_yes:
                map = new HashMap<>();
                map.put("ans", "y");
                if(list.size() < 3) {
                    list.add(map);
                } else {
                    list.set(mViewPager.getCurrentItem(), map);
                }
                if (mViewPager.getCurrentItem() == 2) {
                    buttonGo.setVisibility(View.VISIBLE);
                    buttonYes.setVisibility(View.INVISIBLE);
                    buttonNo.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.button_no:
                map = new HashMap<>();
                map.put("ans", "n");
                if(list.size() < 3) {
                    list.add(map);
                } else {
                    list.set(mViewPager.getCurrentItem(), map);
                }
                if (mViewPager.getCurrentItem() == 2) {
                    buttonGo.setVisibility(View.VISIBLE);
                    buttonYes.setVisibility(View.INVISIBLE);
                    buttonNo.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.btn_go:
                // UPDATE: 2020/4/11 这里添加注释
//                if(getDishWithOkHttp()) {
//                    ActivityOptions oc2 = ActivityOptions.makeSceneTransitionAnimation(QuestionActivity.this);
//                }
                Intent intent = new Intent(QuestionActivity.this, RecommendResultActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        if(mViewPager.getCurrentItem() < 2) {
            mViewPager.arrowScroll(2);
        }
    }
    /**
     * 写一个get函数  如果用retrofit应该就不用了吧;本地手写数据
     */
    private void questionList(){
        String content1 = "spicy";
        String content2 ="seafood";
        String content3 ="oil";
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



}
