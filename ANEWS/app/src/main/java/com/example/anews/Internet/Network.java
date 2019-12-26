package com.example.anews.Internet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//新闻API的调用函数
public interface Network {
    //https://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=d7fc8ca8679fc67b
    //https://api.jisuapi.com/news/search?keyword=姚明&appkey=d7fc8ca8679fc67b
    @GET("news/get")
    Call<NewsBean>getNews(@Query("channel")String channel, @Query("start")int start, @Query("num")int num, @Query("appkey")String appkey);

    @GET("news/search")
    Call<SearchBean>searchNews(@Query("keyword")String keyword,@Query("appkey")String appkey);
}
