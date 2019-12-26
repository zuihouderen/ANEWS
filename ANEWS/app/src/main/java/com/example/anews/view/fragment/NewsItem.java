package com.example.anews.view.fragment;

import android.widget.ImageView;

import androidx.annotation.Nullable;

//新闻项的信息类
public class NewsItem {
    private String mPicURL;
    private String mTitle;
    private String mTime;
    private String mContent;
    private String mChannel;

    public NewsItem(String picURL, String title, String time, String content, String channel){
        this.mPicURL = picURL;
        this.mTitle = title;
        this.mTime = time;
        this.mContent = content;
        this.mChannel = channel;
    }

    public String getmPicURL() {
        return mPicURL;
    }

    public void setmPicURL(String mPicURL) {
        this.mPicURL = mPicURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmChannel() {
        return mChannel;
    }

    public void setmChannel(String mChannel) {
        this.mChannel = mChannel;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        NewsItem item = (NewsItem)obj;
        return this.mTitle.equals(item.getmTitle());
    }
}
