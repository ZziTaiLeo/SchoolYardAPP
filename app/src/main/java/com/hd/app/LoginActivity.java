package com.hd.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import connect.ConnectTool;
import module.User;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPwd;
    private TextView visitorLogin;
    private SharedPreferences pref;//记住密码功能
    private SharedPreferences.Editor editor;
    private int state = -1;
    private User user = new User();

    private void initView() {
        loginButton = (Button)findViewById(R.id.login_button);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        rememberPwd =(CheckBox)findViewById(R.id.remember_pwd_box);
        visitorLogin = (TextView)findViewById(R.id.visitor_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        rememberPassword();
        sendUserInformation();
        setListner();
    }

    private void sendUserInformation() {
    }

    private void rememberPassword() {
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember)
        {
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            Log.d("LoginActivity","执行了这个"+account);
            if(!password.isEmpty()) {
                passwordEdit.setText(password);
                rememberPwd.setChecked(true);
            }
            Log.d("LoginActivity","执行了这个2"+password);

        }
    }



    private void setListner() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(account.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    user.setAccount(account);
                    user.setPassword(password);
                    //sendLoginRequest();
                    state = 0;
                    switch (state) {
                        case 1:
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            break;
                        case 0: {//登录成功的状态，状态码0
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            editor = pref.edit();
                            if(rememberPwd.isChecked())//检查复选框的选中状态
                            {
                                editor.putBoolean("remember_password",true);
                                editor.putString("account",account);
                                editor.putString("password",password);
                            }
                            else
                            {
                                editor.putString("account",account);
                                editor.putString("password","");
                            }
//                            else
//                            {
//                                editor.clear();
//                            }
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        default: {
                            Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        visitorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendLoginRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ConnectTool connectTool = new ConnectTool();
                    user=connectTool.login(user);
                    if(!user.getAccount().isEmpty())
                        state = 0;//服务器端返回的账户不是空值

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}