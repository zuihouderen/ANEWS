package com.example.anews.presenter;

import android.util.Log;

import com.example.anews.model.IMainM;
import com.example.anews.model.MainM;
import com.example.anews.view.IMainV;
import com.example.anews.view.fragment.NewsItem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

//为新闻页面获取新闻和推荐的类
public class MainP implements IMainP {
    private IMainV mIMainV;
    private IMainM mIMainM;
    private List<NewsItem>mNewsItems;
    private int ReplyType = -1;

    private static final String TAG = "MainP";
    public MainP(IMainV iMainV){
        this.mIMainV = iMainV;
    }

    //获取新闻
    @Override
    public void doGetNews(String channel, int start, int num) {
        ReplyType = 0;
        mIMainM = new MainM(this);
        mIMainM.getNews(channel, start, num);
    }
    //搜索新闻
    @Override
    public void doSearchNews(String keyword) {
        ReplyType = 1;
        mIMainM = new MainM(this);
        mIMainM.searchNews(keyword);
    }
    //设置获取到的新闻信息，根据返回类型调用View不同的UI修改方法
    @Override
    public void setNewsItemList(String channel,List<NewsItem> newsList) {
        Log.d(TAG, "setNewsItemList: ");
        this.mNewsItems = newsList;
        switch (ReplyType){
            case 0:
            case 1:
                mIMainV.onResult(channel, mNewsItems);
                break;
            case 2:
                mIMainV.onLoadMore(mNewsItems);
                break;
            case 3:
                mIMainV.onRefresh(mNewsItems);
                break;
        }
    }
    //加载更多
    @Override
    public void doLoadMore(String channel, int start) {
        ReplyType = 2;
        mIMainM = new MainM(this);
        mIMainM.loadMore(channel,start);
    }
    //下拉刷新
    @Override
    public void doRefresh(String channel) {
        ReplyType = 3;
        mIMainM = new MainM(this);
        mIMainM.refresh(channel);
    }
    //推荐新闻
    public void doRecommend(Map<String,Number> channel){
        ReplyType=3;
        mIMainM=new MainM(this);
        int total = 0;
        Iterator iterator=channel.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            total += (Integer) entry.getValue();
            Log.d(""+entry.getKey(),""+entry.getValue() + " total " +total);
        }
        iterator=channel.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            int num = ((Integer)entry.getValue()*10)/total;
            num = num==0?1:num;
            mIMainM.getNews(entry.getKey().toString(),0, num);
            Log.d(""+entry.getKey(),""+entry.getValue()+" num "+num);
        }
    }


}
