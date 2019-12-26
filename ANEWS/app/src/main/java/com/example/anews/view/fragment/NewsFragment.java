package com.example.anews.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anews.R;
import com.example.anews.presenter.HistoryP;
import com.example.anews.presenter.IHistoryP;
import com.example.anews.presenter.IMainP;
import com.example.anews.presenter.MainP;
import com.example.anews.presenter.RecommendP;
import com.example.anews.view.IHistoryV;
import com.example.anews.view.IMainV;
import com.example.anews.view.MainActivity;
import com.example.anews.view.NewsDetailActivity;
import com.example.anews.view.history.HistoryItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//每一个新闻频道的fragment
public class NewsFragment extends Fragment implements IMainV, IHistoryV,RecommendV {
    private String newsType;
    private Context mContext;
    private List<NewsItem> mNewsItemList;
    private IMainP mIMainP;
    private IHistoryP mIHistoryP;
    private int START;

    private FragmentRVAdaptor mFragmentRVAdaptor;
    private SmartRefreshLayout mSmartRefreshLayout;

    private HashMap<String,Number> mChannelMap;
    private RecommendP mRecommendP;


    public NewsFragment(Context context, String newstype, List<NewsItem> newsItems){
        this.newsType = newstype;
        this.mContext = context;
        this.mNewsItemList = newsItems;
        mFragmentRVAdaptor = new FragmentRVAdaptor(mContext,mNewsItemList);
        this.mIMainP = new MainP(this);
        this.mIHistoryP = new HistoryP(this);
        this.START = MainActivity.NUM;
        if(newstype.equals("头条")){
            mChannelMap = new HashMap<>();
            mRecommendP = new RecommendP(this);
            this.newsType = "推荐";
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        view = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.news_item_recyclerView);
        mSmartRefreshLayout = view.findViewById(R.id.fragment_refresher);
        if(mFragmentRVAdaptor == null)
            mFragmentRVAdaptor = new FragmentRVAdaptor(mContext,mNewsItemList);

        //下拉刷新事件设置
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(mContext, "刷新成功", Toast.LENGTH_SHORT).show();
                if(newsType.equals("推荐"))
                    recommendRefresh();
                else
                    mIMainP.doRefresh(newsType);
            }
        });
        //上拉加载更多事件设置
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(START <= 400) {
                    Toast.makeText(mContext, "上拉加载", Toast.LENGTH_SHORT).show();
                    mIMainP.doLoadMore(newsType, START);
                }
                else{
                    Toast.makeText(mContext, "已经到底了", Toast.LENGTH_SHORT).show();
                    mSmartRefreshLayout.finishLoadMore(1000);
                }
            }
        });

        //设置每个新闻项的点击事件：查看新闻详情
        mFragmentRVAdaptor.setOnItemClickListener(new FragmentRVAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mIHistoryP.addHistory(mNewsItemList.get(position).getmPicURL(),
                        mNewsItemList.get(position).getmTitle(),
                        mNewsItemList.get(position).getmContent(),
                        mNewsItemList.get(position).getmChannel());
                Log.d(mNewsItemList.get(position).getmChannel(), "onItemClick: add history");
                ArrayList<String>data = new ArrayList<>();
                data.add(mNewsItemList.get(position).getmTitle());
                data.add(mNewsItemList.get(position).getmPicURL());
                data.add(mNewsItemList.get(position).getmContent());
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data",data);
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(mContext,"on item click "+position+' '+mNewsItemList.get(position).getmTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        //设置每个新闻项的长点击事件：收藏、删除
        mFragmentRVAdaptor.setOnItemLongClickListener(new FragmentRVAdaptor.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(final int position) {
                Toast.makeText(mContext,"on item long click "+position+' '+mNewsItemList.get(position).getmTime(),Toast.LENGTH_SHORT).show();
                final String[] items = {"收藏", "删除"};
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(mContext).setIcon(R.mipmap.ic_launcher)
                        .setTitle("选项")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(mContext, items[i], Toast.LENGTH_SHORT).show();
                                if(items[i].equals("收藏")){
                                    mIHistoryP.addCollection(mNewsItemList.get(position).getmPicURL(),
                                            mNewsItemList.get(position).getmTitle(),
                                            mNewsItemList.get(position).getmContent(),
                                            mNewsItemList.get(position).getmChannel());
                                }
                                if(items[i].equals("删除")){
                                    mNewsItemList.remove(position);
                                    mFragmentRVAdaptor.notifyDataSetChanged();
                                    Toast.makeText(mContext, items[i]+"成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.create().show();
            }
        });
        @SuppressLint("WrongConstant")
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mFragmentRVAdaptor);
        return view;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    //提供传感器事件的刷新函数
    public void refresh() {
        mSmartRefreshLayout.autoRefresh();
    }

    //做推荐新闻的刷新函数
    private void recommendRefresh(){
        mRecommendP.doQueryCollection(20);
    }

    @Override
    public void onResult(String type, List<NewsItem> newsBeans) {

    }

    //做刷新事件的返回新闻信息
    @Override
    public void onRefresh(List<NewsItem> newsItems) {
        int size = mNewsItemList.size();
        for (NewsItem item: newsItems) {
            if(!mNewsItemList.contains(item)) {
                Log.d("on refresh", "onRefresh: " + item.getmTitle());
                mNewsItemList.add(0, item);
            }
        }
        if(size != mNewsItemList.size())
            mFragmentRVAdaptor.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh(1000);
    }

    //做上拉加载事件的返回新闻信息
    @Override
    public void onLoadMore(List<NewsItem> newsItems) {
        START += 10;
        mNewsItemList.addAll(newsItems);
        mFragmentRVAdaptor.notifyDataSetChanged();
        mSmartRefreshLayout.finishLoadMore(1000);
    }

    @Override
    public void onAskRecordResult(List<HistoryItem> newsItems) {

    }

    //查询新闻项的收藏情况
    @Override
    public void onQueryCollectionResult(int resultCode) {
        if(resultCode == 0)
            Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
        if(resultCode == 1)
            Toast.makeText(mContext, "收藏过了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deal(List<String> list) {
        for(int i=0;i<list.size();i++){
            String key=list.get(i);
            if(mChannelMap.containsKey(key)){
                int val=mChannelMap.get(key).intValue();
                val++;
                mChannelMap.put(key,val);
                Log.d("值",""+val);
            }
            else {
                mChannelMap.put(key,1);
            }
        }
        mRecommendP.doQueryHistory();
    }

    @Override
    public void dealHChannel(List<String> list) {
        for(int i=0;i<list.size();i++){
            String key=list.get(i);
            if(mChannelMap.containsKey(key)){
                int val=mChannelMap.get(key).intValue();
                val++;
                mChannelMap.put(key,val);
                Log.d("值",""+val);
            }
            else {
                mChannelMap.put(key,1);
            }
        }
        mIMainP.doRecommend(mChannelMap);
        mChannelMap.clear();
    }
}
