package com.blogspot.babenkodmitry.aboutme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by dmitrybabenko on 5/17/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<CardDataObjectHolder> {
    private static String TAG = "RecyclerViewAdapter";
    private ArrayList<CardDataObject> mDataProvider;

    public RecyclerViewAdapter(ArrayList<CardDataObject> dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public CardDataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_card, parent, false);
        return new CardDataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(CardDataObjectHolder holder, int position) {
        holder.label.setText(mDataProvider.get(position).getTitle());
        holder.dateTime.setText(mDataProvider.get(position).getDescription());
        holder.icon.setImageDrawable(mDataProvider.get(position).getIcon());
    }

    public void addItem(CardDataObject dataObj, int index) {
        mDataProvider.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataProvider.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataProvider.size();
    }
}
