package com.example.anews.view;

import com.example.anews.view.fragment.NewsItem;

import java.util.List;

//主界面和fragment实现的接口，用于新闻的获取
public interface IMainV {
    //新闻初始化时使用的接口
    void onResult(String type, List<NewsItem> newsItems);
    //获取刷新返回的新闻信息
    void onRefresh(List<NewsItem>newsItems);
    //获取上拉加载获取的新闻信息
    void onLoadMore(List<NewsItem>newsItems);
}
