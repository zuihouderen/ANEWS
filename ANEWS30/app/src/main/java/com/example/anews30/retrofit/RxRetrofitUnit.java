package com.example.anews30.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.anews30.bean.NewsGson;
import com.example.anews30.constant.AppConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitUnit {
    private static RxRetrofitUnit instance;
    private Context mContext;
    private Retrofit retrofit;

    private RxRetrofitUnit() {
        OkHttpClient client=new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
                .build();
        //1.创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BaseUrl) // 定义访问的主机地址
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())  //解析方法
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加 RxJava 适配器
                .build();
    }
    /**
     * 单例模式
     *
     * @return
     */
    public static RxRetrofitUnit getInstance() {
        if (instance == null) {
            synchronized (RxRetrofitUnit.class){
                if (instance==null){
                    instance = new RxRetrofitUnit();
                }
            }
        }
        return instance;
    }


    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

//    //提供接口方法
//    @SuppressLint("CheckResult")public void getMessage(final CallBack call)
//    {
//        Observable< NewsGson > dtoObservable=RxRetrofitUnit.getInstance().create(ApiService.class).getNewsData(AppConfig.Key,"10");
//        //调度线程
//
//        final Disposable subscribe = dtoObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<NewsGson>() {
//                    @Override
//                    public void accept(NewsGson listMessage) throws Exception {
//                        if (listMessage == null) {
//                            call.onError();
//                            Log.d("66", "成功联网 ,listMessage为空" );
//                        } else {
//                            call.onSuccess(listMessage);
//                            Log.d("55", "成功联网 调用API" );
//                        }
//                    }
//                });
//    }
//    //这是一个回调接口
//    public interface  CallBack{
//        void onSuccess(NewsGson newsGson);
//
//        void onError();
//    }
}
