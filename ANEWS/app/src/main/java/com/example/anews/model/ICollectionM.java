package com.example.anews.model;

import com.example.anews.bmob.Collection;

import java.util.List;

public interface ICollectionM {
    void updateCollection();
    void cancelCollection(String objectID);
    void queryCollection(int limit);
    void queryCollectionAll();
}
