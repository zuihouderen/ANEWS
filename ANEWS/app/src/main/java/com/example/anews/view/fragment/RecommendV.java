package com.example.anews.view.fragment;

import java.util.List;

//做推荐时返回信息用的接口
public interface RecommendV {
    public void deal(List< String > list);
    public void dealHChannel(List< String > list);
}
