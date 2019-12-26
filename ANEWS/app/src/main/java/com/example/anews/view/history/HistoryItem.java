package com.example.anews.view.history;

import androidx.annotation.Nullable;

import com.example.anews.view.fragment.NewsItem;


//历史记录adaptor的内容项
public class HistoryItem {
    private String mPicURL;
    private String mTitle;
    private String mContent;
    private String mObjectID;

    public HistoryItem(String picURL, String title, String content, String objectID){
        this.mPicURL = picURL;
        this.mTitle = title;
        this.mContent = content;
        this.mObjectID = objectID;
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

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmObjectID() {
        return mObjectID;
    }

    public void setmObjectID(String mObjectID) {
        this.mObjectID = mObjectID;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        NewsItem item = (NewsItem)obj;
        return this.mTitle.equals(item.getmTitle());
    }
}
