package com.example.anews.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anews.R;

//recycler view的view holder
public class RVHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTitleTV;
    private TextView mTimeTV;

    public RVHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.news_item_iv);
        mTitleTV = itemView.findViewById(R.id.news_item_title);
        mTimeTV = itemView.findViewById(R.id.news_item_time);
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public TextView getmTitleTV() {
        return mTitleTV;
    }

    public TextView getmTimeTV() {
        return mTimeTV;
    }
}
