package com.hd.app.bean;

import java.util.List;

public class Dish {
    private String dishNum;

    public String getDishNum() {
        return dishNum;
    }

    public void setDishNum(String dishNum) {
        this.dishNum = dishNum;
    }

    public String getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(String idRecord) {
        this.idRecord = idRecord;
    }

    public List getDishList() {
        return dishList;
    }

    public void setDishList(List dishList) {
        this.dishList = dishList;
    }

    private String idRecord;
    private List dishList;
}
