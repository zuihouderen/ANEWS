package com.example.anews.view.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anews.R;

import java.util.List;

//历史记录页面list view的adaptor
public class HistoryAdaptor extends BaseAdapter {
    private Context mContext;
    private String mType;
    private List<HistoryItem> mHistoryItems;
    private LayoutInflater mLayoutInflater;

    HistoryAdaptor(Context context, String type, List<HistoryItem>historyItems){
        mContext = context;
        this.mType =type;
        mHistoryItems = historyItems;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mHistoryItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mHistoryItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mLayoutInflater.inflate(R.layout.news_item,null);

        TextView titleTV = view.findViewById(R.id.news_item_title);
        TextView timeTV = view.findViewById(R.id.news_item_time);
        ImageView imageView = view.findViewById(R.id.news_item_iv);

        String url = mHistoryItems.get(i).getmPicURL();
        Glide.with(mContext)
                .load(url)
                .into(imageView);
        titleTV.setText(mHistoryItems.get(i).getmTitle());
        timeTV.setText(mType);

        return view;
    }
}
