package com.hd.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestionActivity extends AppCompatActivity {

    private Button submitSuggestionButton;
    private TextView titleNameText;
    private EditText suggestionText;
    private EditText constactWay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        titleNameText = (TextView)findViewById(R.id.title_name);
        titleNameText.setText("建议");
        suggestionText = (EditText)findViewById(R.id.suggestion_text);
        constactWay = (EditText)findViewById(R.id.contact_way);
        submitSuggestionButton = (Button)findViewById(R.id.submit_suggestion_icon);
        submitSuggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suggestionText.length()<=15)
                {
                    Toast.makeText(SuggestionActivity.this,"建议不能少于15字",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(SuggestionActivity.this,"建议提交成功，谢谢您的支持",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
