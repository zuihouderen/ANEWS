package com.example.anews.presenter;

import com.example.anews.model.CollectionM;
import com.example.anews.model.HistoryM;
import com.example.anews.view.history.HistoryItem;
import com.example.anews.view.IHistoryV;

import java.util.List;

//查询历史记录和收藏信息的类
public class HistoryP implements IHistoryP{
    private List<HistoryItem>mHistoryItems;
    private HistoryM fHistoryM;
    private CollectionM fCollectionM;
    private IHistoryV mIHistoryV;

    public HistoryP(IHistoryV iHistoryV){
        this.mIHistoryV=iHistoryV;
    }



//添加历史记录
    @Override
    public void addHistory(String picURL, String title, String content, String channel) {
        fHistoryM = new HistoryM(this);
        fHistoryM.setUrl(picURL);
        fHistoryM.setTitle(title);
        fHistoryM.setContent(content);
        fHistoryM.setChannel(channel);
        fHistoryM.queryAndUpdate();
    }
//添加收藏
    @Override
    public void addCollection(String picURL, String title, String content, String channel) {
        fCollectionM = new CollectionM(this);
        fCollectionM.setUrl(picURL);
        fCollectionM.setTitle(title);
        fCollectionM.setContent(content);
        fCollectionM.setChannel(channel);
        fCollectionM.queryAndUpdate();
    }
//取消收藏
    @Override
    public void cancelCollection(String objectID) {
        fCollectionM.cancelCollection(objectID);
    }
//查询全部历史记录
    @Override
    public void doQueryHistory() {
        fHistoryM = new HistoryM(this);
        fHistoryM.queryHistory();
    }
//查询全部收藏记录
    @Override
    public void doQueryCollection() {
        fCollectionM = new CollectionM(this);
        fCollectionM.queryCollectionAll();
    }

    @Override
    public void setNewsItems(List<HistoryItem> historyItems) {
        this.mHistoryItems = historyItems;
        mIHistoryV.onAskRecordResult(mHistoryItems);
    }

    @Override
    public void setQueryCode(int resultCode) {
        mIHistoryV.onQueryCollectionResult(resultCode);
    }
}
