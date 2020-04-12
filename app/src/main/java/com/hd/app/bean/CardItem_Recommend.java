package com.hd.app.bean;

public class CardItem_Recommend {    private String  mTextResource;
    private String mTitleResource;
    private String mMarkResource;

    public CardItem_Recommend(String title, String text, String mark) {
        mTitleResource = title;
        mTextResource = text;
        mMarkResource=mark;
    }

    public String getText() {
        return mTextResource;
    }
    public String getMark(){
        return mMarkResource;
    }

    public String getTitle() {
        return mTitleResource;
    }
}
