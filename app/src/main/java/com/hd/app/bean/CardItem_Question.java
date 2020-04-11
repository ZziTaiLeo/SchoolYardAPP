package com.hd.app.bean;

public class CardItem_Question {
    private String mTextResource;
    private String mTitleResource;


    public CardItem_Question(String questionId, String question) {
        mTitleResource = questionId;
        mTextResource = question;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

}
