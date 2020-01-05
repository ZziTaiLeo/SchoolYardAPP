package com.hd.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import connect.ConnectTool;
import module.Suggestion;

public class SuggestionActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private String userAccount;
    private Button submitSuggestionButton;
    private TextView titleNameText;
    private TextView suggestionNumText;
    private EditText suggestionText;
    private EditText constactWay;
    private int state = -1;
    private final int Send_Suggestion = 0;
    private Suggestion su ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        titleNameText = (TextView)findViewById(R.id.title_name);
        titleNameText.setText("建议");
        suggestionNumText = (TextView)findViewById(R.id.suggetstion_num);
        suggestionText = (EditText)findViewById(R.id.suggestion_text);
        constactWay = (EditText)findViewById(R.id.contact_way);
        submitSuggestionButton = (Button)findViewById(R.id.submit_suggestion_icon);
        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
        userAccount = pref.getString("account","");


        suggestionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                suggestionNumText.setText(String.valueOf(s.length()));

            }
        });

        submitSuggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suggestionText.length()<=15)
                {
                    Toast.makeText(SuggestionActivity.this,"建议不能少于15字",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Date dt = new Date();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateNowStr = sdf.format(dt);
                    su = new Suggestion(userAccount,suggestionText.getText().toString(),constactWay.getText().toString(),dt.toString());
                    sendSuggestion(su);
                    return;
                }
            }
        });

    }


    private void sendSuggestion(Suggestion su)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectTool connectTool = new ConnectTool();
                    String temp=connectTool.suggestion(su);
                    JSONObject jsonObject = new JSONObject(temp);
                    String s = jsonObject.getString("msg");
                    if(s.equals("success"))
                        state = 0;//服务器端返回成功
                    else
                    {
                        state = 1;
                    }
                    Message message = new Message();
                    message.what = Send_Suggestion;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Send_Suggestion: {
                    switch (state)
                    {
                        case 0:
                        {
                            Toast.makeText(SuggestionActivity.this, "建议提交成功，谢谢您的支持", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 1:
                        {
                            Toast.makeText(SuggestionActivity.this, "网络错误，提交失败", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default:
                        {
                            Toast.makeText(SuggestionActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                default:{
                    break;
                }
            }
        }
    };

}