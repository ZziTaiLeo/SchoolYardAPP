package com.hd.app.adapter;

import android.support.v7.widget.CardView;

public interface CardAdapterQuestion {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
