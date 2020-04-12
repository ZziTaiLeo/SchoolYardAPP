package com.hd.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.app.R;
import com.hd.app.bean.CardItem_Recommend;

import java.util.ArrayList;
import java.util.List;

import static com.hd.app.adapter.CardAdapterRecommend.MAX_ELEVATION_FACTOR;

public class CardPagerAdapterRecommend extends PagerAdapter implements CardAdapterRecommend {

    private List<CardView> mViews;
    private List<CardItem_Recommend> mData;
    private float mBaseElevation;

    public CardPagerAdapterRecommend() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem_Recommend item) {
        mViews.add(null);
        mData.add(item);
    }


    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter_recommend, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem_Recommend item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        TextView markTextView=(TextView)view.findViewById(R.id.cardMark) ;
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        markTextView.setText(item.getMark());
    }

}