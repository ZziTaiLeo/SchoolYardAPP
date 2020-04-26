package com.hd.app.util;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyUtil {
    private int week;//第几周
    private int dayOfWeek;//星期几
    private String TAG = "MyUtil";
    public MyUtil(Date date){
        //获取系统星期 时间 ,计算出第几周
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//北京时间
        // 获取当前月份
        //转换周次顺序 Android中每周是以星期天为第一天
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (--dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        Log.d(TAG, "dayOf week "+dayOfWeek);
         Date date1 = new Date(120, 1, 17);

         Log.d(TAG, "data 2-17 is ="+date1);
         c.setTime(date1);
         long timeOld = c.getTimeInMillis();
         c.setTime(date);
         long timeNew = c.getTimeInMillis();
         long between_days=  ((timeNew-timeOld)/(1000*3600*24));
          week =(int) (between_days /7+1);

    }
    public int getWeek(){
        return week;
    };


    public int getDayOfWeek(){
        return dayOfWeek;
    };


}
