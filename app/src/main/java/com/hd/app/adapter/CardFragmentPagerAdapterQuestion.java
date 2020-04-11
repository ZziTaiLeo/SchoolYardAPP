package com.hd.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.hd.app.fragment.CardFragmentQuestion;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapterQuestion extends FragmentStatePagerAdapter implements CardAdapterQuestion {

    private List<CardFragmentQuestion> mFragments;
    private float mBaseElevation;

    public CardFragmentPagerAdapterQuestion(FragmentManager fm, float baseElevation) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for(int i = 0; i< 4; i++){
            addCardFragment(new CardFragmentQuestion());
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (CardFragmentQuestion) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragmentQuestion fragment) {
        mFragments.add(fragment);
    }

}