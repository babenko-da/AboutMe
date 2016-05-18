package com.blogspot.babenkodmitry.aboutme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dmitrybabenko on 5/17/16.
 */
public class CardDataObjectHolder extends RecyclerView.ViewHolder {
    TextView label;
    TextView dateTime;
    ImageView icon;

    public CardDataObjectHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.card_title);
        label.setTypeface(MainActivity.faceMedium);
        dateTime = (TextView) itemView.findViewById(R.id.card_description);
        dateTime.setTypeface(MainActivity.faceLight);

        icon = (ImageView) itemView.findViewById(R.id.card_icon);
    }
}
