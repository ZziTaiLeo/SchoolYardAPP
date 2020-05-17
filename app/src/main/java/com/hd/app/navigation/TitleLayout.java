package com.hd.app.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hd.app.R;
import com.hd.app.classroom.ClassRoomActivity;

public class TitleLayout extends LinearLayout {
    private  Button back_icon;
    public TitleLayout(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        back_icon = (Button)findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
