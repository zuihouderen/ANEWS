package com.example.anews.presenter;

import java.util.List;

public interface IRecommendP {
    public void doQueryHistory();
    public void doQueryCollection(int limit);
    public void setHChannel(List<String> list);
    public void setCChannel(List<String>list);
}
