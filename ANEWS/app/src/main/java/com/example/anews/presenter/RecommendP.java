package com.example.anews.presenter;

import com.example.anews.model.CollectionM;
import com.example.anews.model.HistoryM;
import com.example.anews.view.fragment.RecommendV;

import java.util.List;

//为新闻推荐做收藏和历史记录查询的类
public class RecommendP implements IRecommendP{

    private HistoryM fHistoryM;
    private CollectionM fCollectionM;
    private RecommendV recommendV;
    public RecommendP(RecommendV recommendV){
        this.recommendV=recommendV;
    }

    /**
     * 做历史记录查询
     */
    public void doQueryHistory(){
        fHistoryM =new HistoryM(this);
        fHistoryM.queryMax();
    }

    /**
     * 做收藏记录查询
     */
    public void doQueryCollection(int limit){
        fCollectionM =new CollectionM(this);
        fCollectionM.queryCollection(20);

    }

    /**
     * 设置HChannel
     * @param list
     */
    public void setHChannel(List<String>list){
        recommendV.dealHChannel(list);
    }

    /**
     * 设置CChannel
     * @param list
     */
    public void setCChannel(List<String>list){
        recommendV.deal(list);
    }

}
