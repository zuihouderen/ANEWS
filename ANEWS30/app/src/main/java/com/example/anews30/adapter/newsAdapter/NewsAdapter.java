package com.example.anews30.adapter.newsAdapter;


import android.content.Context;
import android.view.ViewGroup;

import com.example.anews30.bean.NewsGson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class NewsAdapter extends RecyclerArrayAdapter< NewsGson.NewslistBean > {
        public NewsAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsViewHolder(parent);
        }
    }

