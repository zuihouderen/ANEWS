package com.example.anews.presenter;

import com.example.anews.view.fragment.NewsItem;

import java.util.List;
import java.util.Map;

public interface IMainP {
    void doGetNews(String channel, int start, int num);

    void doSearchNews(String keyword);

    void setNewsItemList(String channel, List<NewsItem> newsList);

    void doLoadMore(String channel, int start);

    void doRefresh(String channel);

    void doRecommend(Map<String,Number> channel);
}
