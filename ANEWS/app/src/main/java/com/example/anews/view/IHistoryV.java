package com.example.anews.view;

import com.example.anews.view.history.HistoryItem;

import java.util.List;

//保存浏览历史和收藏的接口
public interface IHistoryV {
    //查询历史记录
    void onAskRecordResult(List<HistoryItem> newsItems);
    //查询收藏记录
    void onQueryCollectionResult(int resultCode);
}
