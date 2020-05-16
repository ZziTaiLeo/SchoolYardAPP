package com.hd.app.recommend;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hd.app.R;

public class ClassTitleLayout extends  LinearLayout {

    private Button back_icon;
    public ClassTitleLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.classroom_title_bar,this);
        back_icon = (Button)findViewById(R.id.bt_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

    }

}
