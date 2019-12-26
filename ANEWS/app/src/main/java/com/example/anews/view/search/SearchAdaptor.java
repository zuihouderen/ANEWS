package com.example.anews.view.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anews.R;
import com.example.anews.view.fragment.NewsItem;

import java.util.ArrayList;
import java.util.List;

//搜索页面list view的adaptor
public class SearchAdaptor extends BaseAdapter {
    private Context mContext;
    private List<NewsItem> mNewsItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    SearchAdaptor(Context context, List<NewsItem>newsItems){
        mContext = context;
        mNewsItems = newsItems;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNewsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mNewsItems.get(i);
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

        String url = mNewsItems.get(i).getmPicURL();
        Glide.with(mContext)
                .load(url)
                .into(imageView);
        titleTV.setText(mNewsItems.get(i).getmTitle());
        timeTV.setText(mNewsItems.get(i).getmTime());

        return view;
    }
}
