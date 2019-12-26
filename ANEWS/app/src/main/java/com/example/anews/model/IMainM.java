package com.example.anews.model;

public interface IMainM {
    void getNews(String channel, int start, int num);

    void searchNews(String keyword);

    void loadMore(String channel, int start);

    void refresh(String channel);
}
