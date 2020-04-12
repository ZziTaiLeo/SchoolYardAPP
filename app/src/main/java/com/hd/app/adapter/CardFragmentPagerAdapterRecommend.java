package com.hd.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.app.R;
import com.hd.app.bean.CardItem_Recommend;
import com.hd.app.fragment.CardFragmentRecommend;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapterRecommend extends FragmentStatePagerAdapter implements  CardAdapterRecommend {   private List<CardView> mViews;

    private List<CardFragmentRecommend> mFragments;
    private float mBaseElevation;

    public CardFragmentPagerAdapterRecommend(FragmentManager fm, float baseElevation) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for(int i = 0; i< 5; i++){
            addCardFragment(new CardFragmentRecommend());
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
        mFragments.set(position, (CardFragmentRecommend) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragmentRecommend fragment) {
        mFragments.add(fragment);
    }

}
