package com.hd.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import connect.ConnectTool;
import module.PasswordChange;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView titleName;
    private TextView userAccoutText;
    private EditText oldPasswordEdit;
    private EditText newPasswordEdit;
    private EditText confirmNewPwdEdit;
    private Button submitButton;
    private SharedPreferences pref;
    private String userAccount;
    private PasswordChange pa;
    private final int REQUEST_FOR_CHANGE = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        titleName = (TextView)findViewById(R.id.title_name);
        userAccoutText = (TextView)findViewById(R.id.user_id);
        oldPasswordEdit = (EditText)findViewById(R.id.password_old);
        newPasswordEdit = (EditText)findViewById(R.id.password_new);
        submitButton = (Button)findViewById(R.id.submit_new_pwd_icon);
        confirmNewPwdEdit = (EditText)findViewById(R.id.confirm_password_new);

        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
        userAccount = pref.getString("account","");

        userAccoutText.setText(userAccount);
        titleName.setText("修改密码");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }
    private void changePassword()
    {
        if(oldPasswordEdit.getText().length()==0||newPasswordEdit.getText().length()==0||confirmNewPwdEdit.getText().length()==0)
        {
            Toast.makeText(ChangePasswordActivity.this,"三个输入框不能为空！",Toast.LENGTH_SHORT).show();
        }
        else if (confirmNewPwdEdit.getText()!=newPasswordEdit.getText())
        {
            Toast.makeText(ChangePasswordActivity.this,"两次输入的新密码不同！",Toast.LENGTH_SHORT).show();
        }
        else
        {
            pa = new PasswordChange(userAccount,oldPasswordEdit.getText().toString(),newPasswordEdit.getText().toString());
            requestForChangePwd();
        }
    }

    private int state = -1;

    private void requestForChangePwd()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectTool connectTool = new ConnectTool();
                    String temp=connectTool.changePwd(pa);
                    JSONObject jsonObject = new JSONObject(temp);
                    String s = jsonObject.getString("msg");
                    if(s.equals("success"))
                    {
                        state = 0;
                    }
                    else
                    {
                        state =1;
                    }
                    Message message = new Message();
                    message.what = REQUEST_FOR_CHANGE;
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
                case REQUEST_FOR_CHANGE: {
                    switch (state)
                    {
                        case 0:
                        {
                            Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case 1:
                        {
                            Toast.makeText(ChangePasswordActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        default:
                        {
                            Toast.makeText(ChangePasswordActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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
