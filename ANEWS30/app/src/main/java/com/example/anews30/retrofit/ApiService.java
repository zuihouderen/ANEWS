package com.example.anews30.retrofit;

import com.example.anews30.bean.NewsGson;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //@GET("{type}/")
    //Observable<NewsGson> getNewsData(@Path("type") String type,@Query("key") String key, @Query("num") String num);
//    @GET("house/")
//    Call< NewsGson>getNewsData(@Query("key") String key, @Query("num") String num);
    @GET("house/")
    Observable< NewsGson> getNewsData(@Query("key") String key, @Query("num") String num);
    @GET("allnews/")
    Observable< NewsGson> getNewsData(@Query("key") String key, @Query("num") String num,@Query("col")String col);

}