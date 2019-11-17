package com.example.anews30;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Util;
import com.example.anews30.adapter.newsAdapter.NewsAdapter;
import com.example.anews30.bean.NewsGson;
import com.example.anews30.constant.AppConfig;
import com.example.anews30.retrofit.ApiService;
import com.example.anews30.retrofit.RxRetrofitUnit;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public List< NewsGson.NewslistBean > newslistBeans=new ArrayList<>();
    private EasyRecyclerView recyclerView;
    private NewsAdapter newsAdapter=new NewsAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.news_detail);

        getData();
        //setView();

    }

    @SuppressLint("CheckResult")
    private void getData() {
        ApiService apiManager = RxRetrofitUnit.getInstance().create(ApiService.class);//这里采用的是Java的动态代理模式
        apiManager.getNewsData(AppConfig.Key,"10")
                .subscribeOn(Schedulers.io())
                .map(new Function< NewsGson, List<NewsGson.NewslistBean> >() {
                    @Override
                    public List< NewsGson.NewslistBean > apply(NewsGson newsGson) throws Exception {
                        for(NewsGson.NewslistBean newslistBean: newsGson.getNewslist()){
                            NewsGson.NewslistBean newslistBean1=new NewsGson.NewslistBean();
                            newslistBean1.setTitle(newslistBean.getTitle());
                            newslistBean1.setCtime(newslistBean.getCtime());
                            newslistBean1.setDescription(newslistBean.getDescription());
                            newslistBean1.setPicUrl(newslistBean.getPicUrl());
                            newslistBean1.setUrl(newslistBean.getUrl());
                            newslistBeans.add(newslistBean1);
                        }
                        return  newslistBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer< List< NewsGson.NewslistBean > >() {
                    @Override
                    public void accept(List< NewsGson.NewslistBean > newslistBeans) throws Exception {
                        newsAdapter.addAll(newslistBeans);
                        newslistBeans.clear();
                        setView();
                    }
                });

//        RxRetrofitUnit.getInstance().getMessage(new RxRetrofitUnit.CallBack() {
//            @Override
//            public void onSuccess(NewsGson newsGson) {
//                for (NewsGson.NewslistBean newslistBean : newsGson.getNewslist()) {
//                   NewsGson.NewslistBean newslistBean1=new NewsGson.NewslistBean();
//                    newslistBean1.setTitle(newslistBean.getTitle());
//                    newslistBean1.setCtime(newslistBean.getCtime());
//                    newslistBean1.setDescription(newslistBean.getDescription());
//                    newslistBean1.setPicUrl(newslistBean.getPicUrl());
//                    newslistBean1.setUrl(newslistBean.getUrl());
//                    newslistBeans.add(newslistBean1);
//                }
//                setView();
//            }
//
//            @Override
//            public void onError() {
//                Log.d("555","555");
//            }
//        });
    }

    public void setView(){
        recyclerView=(EasyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //newsAdapter.addAll(newslistBeans);
        recyclerView.setAdapter(newsAdapter);
        //Log.e(newslistBeans.get(3).getCtime(),"hdsahj");

        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY,2);//颜色 & 高度 & 左边距 & 右边距
        itemDecoration.setDrawLastItem(true);//有时候你不想让最后一个item有分割线,默认true.
        itemDecoration.setDrawHeaderFooter(false);//是否对Header于Footer有效,默认false.
        recyclerView.addItemDecoration(itemDecoration);

//        newsAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
//            @Override
//            public void onMoreShow() {
//                //addData();
//
//                getData();
//            }
//
//            @Override
//            public void onMoreClick() {
//
//            }
//        });

        //写刷新事件
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       newsAdapter.clear();
                       getData();
                    }
                },1000);
            }
        });
        //点击事件
        newsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //position不包含Header
//                Toast.makeText(getApplicationContext(),
//                        position+"", Toast.LENGTH_LONG).show();
                ArrayList<String> data = new ArrayList<String>();
                data.add(newsAdapter.getAllData().get(position).getPicUrl());
                data.add(newsAdapter.getAllData().get(position).getUrl());
                Intent intent;
                intent = new Intent(MainActivity.this,NewsDetailActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //长点击事件
        newsAdapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                Toast.makeText(getApplicationContext(),
                        position+"", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }
//    void addData(){
//        recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                newsAdapter.addAll(newslistBeans);
//            }
//        },1000);
//    }
}