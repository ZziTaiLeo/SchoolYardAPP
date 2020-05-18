package com.hd.app.util;

public class PlanDistanceUtil {
    private int distance;
    private int meters;
    private int miles;

    public String PlanDistanceUtil(int distance) {
        this.distance = distance;
        StringBuilder stringBuilder = new StringBuilder();
        meters = distance % 1000;
        miles = distance / 1000;
        if (meters > 0) {
            stringBuilder.append(meters).append("公里");

        }
        stringBuilder.append(meters).append("米");
        return stringBuilder.toString();
    }
}
