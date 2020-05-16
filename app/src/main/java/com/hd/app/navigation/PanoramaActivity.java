package com.hd.app.navigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.hd.app.R;

/**
 * 全景图活动
 * http://yiban.fzu.edu.cn/m/pcindex.html
 */
public class PanoramaActivity extends AppCompatActivity {


    public static final String url = "http://yiban.fzu.edu.cn/m/pcindex.html";
    private WebView webView ;
    private TextView titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        titleName = (TextView)findViewById(R.id.title_name);

        titleName.setText("福大全景图");
    }

}
