package com.example.anews.presenter;

import com.example.anews.view.history.HistoryItem;

import java.util.List;

public interface IHistoryP {
    void addHistory(String picURL, String title, String content, String channel);

    void addCollection(String picURL, String title, String content, String channel);

    void cancelCollection(String objectID);

    void doQueryHistory();

    void doQueryCollection();

    void setNewsItems(List<HistoryItem> newsItems);

    void setQueryCode(int resultCode);
}
