package com.hd.app.util;

/**
 * 路径规划类的时间格式处理
 */
public class PlanDurationUtil {
    private int day;
    private int hours;
    private int minutes;
    private int seconds;

    public String PlanDurationUtil(int seconds) {

        this.seconds = seconds;

        minutes = (seconds / 60)%60;
        hours = (seconds / 3600)%24;
        day = seconds / 86400;
        StringBuilder stringBuilder = new StringBuilder();

        if (day > 0) {
            stringBuilder.append(String.valueOf(day)).append("天");
        }
        if (hours > 0) {
            stringBuilder.append(String.valueOf(hours)).append("小时");

        }
        stringBuilder.append(minutes).append("分钟");
        return stringBuilder.toString();

    }

}
