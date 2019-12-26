package com.example.anews.model;

import com.example.anews.bmob.History;

import java.util.List;

public interface IHistoryM {

    void updateHistory();
    void queryAndUpdate();
    void queryMax();
    void updateCount(History history, final int count);
    void queryHistory();

}
