package com.example.anews.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.anews.Constant;
import com.example.anews.Internet.Network;
import com.example.anews.Internet.NewsBean;
import com.example.anews.Internet.SearchBean;
import com.example.anews.presenter.IMainP;
import com.example.anews.view.fragment.NewsItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//调用API获取新闻的类
public class MainM implements IMainM {
    private List<NewsItem>mNewsItems;
    private IMainP mIMainP;
    private final Retrofit mRetrofit;

    private static final String TAG = "MainM";
    public MainM(IMainP iMainP){
        this.mIMainP = iMainP;
        mNewsItems = new ArrayList<>();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.jisuapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //按频道获取新闻
    @Override
    public void getNews(String channel, int start, int num) {
        Log.d(TAG, "getNews: ");
        Network request = mRetrofit.create(Network.class);
        Call<NewsBean>call = request.getNews(channel,start,num, Constant.NewsAppKey);

        NetAsyncTask<NewsBean>netAsyncTask = new NetAsyncTask<>(call);
        netAsyncTask.execute();
    }
    //按关键词搜索新闻
    @Override
    public void searchNews(String keyword) {
        Log.d(TAG, "searchNews: "+keyword);
        Network request = mRetrofit.create(Network.class);
        Call<SearchBean>call = request.searchNews(keyword,Constant.NewsAppKey);

        NetAsyncTask<SearchBean>netAsyncTask = new NetAsyncTask<>(call);
        netAsyncTask.execute();
    }

    //按频道加载更多
    @Override
    public void loadMore(String channel, int start) {
        Network request = mRetrofit.create(Network.class);
        Call<NewsBean>call = request.getNews(channel,start,10,Constant.NewsAppKey);

        Log.d(TAG, "loadMore: " + channel);
        NetAsyncTask<NewsBean>netAsyncTask = new NetAsyncTask<>(call);
        netAsyncTask.execute();
    }
    //按频道刷新新闻
    @Override
    public void refresh(String channel) {
        Network request = mRetrofit.create(Network.class);
        Call<NewsBean>call = request.getNews(channel,0,5,Constant.NewsAppKey);

        Log.d(TAG, "refresh: " + channel);
        NetAsyncTask<NewsBean>netAsyncTask = new NetAsyncTask<>(call);
        netAsyncTask.execute();
    }

    //异步请求
    protected class NetAsyncTask<type> extends AsyncTask<Void,Integer, Response<type>>{
        private Call<type> mCall;

        public NetAsyncTask(Call<type>call){
            this.mCall = call;
        }

        @Override
        protected Response<type> doInBackground(Void... voids) {
            Response<type>response = null;
            try {
                Log.d(TAG, "doInBackground: do excute");
                response = mCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response<type> typeResponse) {
//            super.onPostExecute(typeResponse);
            if(typeResponse == null) {
                Log.i("NET ERROR", "onPostExecute: ");
                return;
            }
            if(typeResponse.body() instanceof NewsBean){
                List<NewsBean.ResultBean.ListBean>listBeans = ((NewsBean) typeResponse.body()).getResult().getList();
                Log.d(TAG, "onPostExecute:  news size" + listBeans.size());
                NewsBean.ResultBean.ListBean listBean;
                for(int i = 0;i < listBeans.size();i++){
                    listBean = listBeans.get(i);
                    String title = listBean.getTitle();
                    String time = listBean.getTime();
                    String picURL = listBean.getPic();
                    String content = listBean.getContent();
                    String channel = ((NewsBean) typeResponse.body()).getResult().getChannel();
                    NewsItem newsItem = new NewsItem(picURL,title,time,content,channel);
                    mNewsItems.add(newsItem);
                }

                mIMainP.setNewsItemList(((NewsBean) typeResponse.body()).getResult().getChannel(),mNewsItems);
            }
            if(typeResponse.body() instanceof  SearchBean){
                List<SearchBean.ResultBean.ListBean>listBeans = ((SearchBean) typeResponse.body()).getResult().getList();
                SearchBean.ResultBean.ListBean listBean;
                for(int i=0;i < listBeans.size();i++){
                    listBean = listBeans.get(i);
                    String title = listBean.getTitle();
                    String time = listBean.getTime();
                    String picURL = listBean.getPic();
                    String content = listBean.getContent();
                    String channel = null;
                    NewsItem newsItem = new NewsItem(picURL,title,time,content,channel);
                    mNewsItems.add(newsItem);
                }

                mIMainP.setNewsItemList(((SearchBean) typeResponse.body()).getResult().getKeyword(),mNewsItems);
            }
        }
    }
}
