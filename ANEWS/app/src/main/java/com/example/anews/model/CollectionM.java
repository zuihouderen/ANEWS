package com.example.anews.model;

import android.util.Log;

import com.example.anews.bmob.Collection;
import com.example.anews.presenter.HistoryP;
import com.example.anews.presenter.RecommendP;
import com.example.anews.view.history.HistoryItem;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

//对收藏做云数据库修改的类
public class CollectionM implements ICollectionM {
    private List<HistoryItem>mHistoryItems;
    private BmobUser bmobUser;
    private String url;
    private String title;
    private String channel;
    private String content;
    private HistoryP mHistoryP;
    private RecommendP recommendP;

    public CollectionM(RecommendP recommendP) {
        this.recommendP=recommendP;
        this.bmobUser = BmobUser.getCurrentUser(BmobUser.class);
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CollectionM(HistoryP recommendP){
        this.mHistoryP =recommendP;
        this.bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        this.mHistoryItems = new ArrayList<>();
    }



    /**
     *  更新我的收藏
     */
    @Override
    public void updateCollection(){
        Collection collection=new Collection();
        collection.setAuthor(bmobUser);
        collection.setTitle(title);
        collection.setUrl(url);
        collection.setChannel(channel);
        collection.setContent(content);
        collection.save(new SaveListener< String >() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.d("SUCCESS:","插入收藏记录成功");
                }
                else{
                    Log.e("fail",e.toString());
                }
            }
        });
    }

    /**
     * 取消收藏
     * @param objectID
     */

    @Override
    public void cancelCollection(String objectID){
        Collection collection=new Collection();
        collection.setObjectId(objectID);
        collection.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("SUCCESS","取消收藏成功");
                    mHistoryP.setQueryCode(1);
                }
                else{
                    Log.d("取消收藏失败",""+e);
                    mHistoryP.setQueryCode(0);
                }
            }
        });
    }


    /**
     * 查找同一个用户收藏过的新闻，限定条数
     * @param limit
     */
    @Override
    public void queryCollection(int limit){
        BmobQuery<Collection> query=new BmobQuery<Collection>();
        query.addWhereEqualTo("author",bmobUser);
        query.order("-UpdatedAt");
        query.setLimit(limit);
        final List<String>strings=new ArrayList<>();
        query.findObjects(new FindListener< Collection >() {
            @Override
            public void done(List< Collection > list, BmobException e) {
                if(e==null){
                    Log.d("收藏查询成功",""+list.size());

                    for(int i=0;i<list.size();i++) {
                        strings.add(list.get(i).getChannel());
                    }
                    /**
                     * 获取收藏频道
                     */
                   recommendP.setCChannel(strings);
                    //Log.d("内部的SIZE",strings.size()+"");
                }
                else {
                    Log.e("查询失败","获取历史记录失败");
                }
            }
        });
    }



    /**
     * 查找同一个用户收藏过的所有新闻
     */
    public void queryCollectionAll(){
        BmobQuery<Collection> query=new BmobQuery<Collection>();
        query.addWhereEqualTo("author",bmobUser);
        query.order("-UpdatedAt");
        final List<Collection>result=new ArrayList<>();
        query.findObjects(new FindListener< Collection >() {
            @Override
            public void done(List< Collection > list, BmobException e) {
                if(e==null){
                    Log.d("查询成功",""+list.size());

                    for(int i=0;i<list.size();i++) {
                        String picURL = list.get(i).getUrl();
                        String content = list.get(i).getContent();
                        String title = list.get(i).getTitle();
                        String object = list.get(i).getObjectId();

                        Collection collection=new Collection();
                        collection.setUrl(list.get(i).getUrl());
                        collection.setContent(list.get(i).getContent());
                        collection.setTitle(list.get(i).getTitle());
                        collection.setChannel(list.get(i).getChannel());
                        collection.setAuthor(list.get(i).getAuthor());
                        result.add(collection);

                        HistoryItem historyItem = new HistoryItem(picURL,title,content,object);
                        mHistoryItems.add(historyItem);
                    }
                    Log.d("内部的SIZE",result.size()+"");
                    mHistoryP.setNewsItems(mHistoryItems);
                }
                else {
                    Log.e("查询失败","获取收藏记录失败"+e);
                }
            }
        });
    }

    /**
     * 查询新闻是否被用户收藏，不是则添加收藏，是则返回相应返回值
     */
    public void queryAndUpdate(){

        BmobQuery<Collection>query=new BmobQuery<Collection>();
        query.addWhereEqualTo("author",bmobUser);
        BmobQuery<Collection> historyBmobQuery=new BmobQuery<Collection>();
        historyBmobQuery.addWhereEqualTo("url",url);
        List<BmobQuery<Collection>> bmobQueries=new ArrayList<BmobQuery< Collection>>();
        bmobQueries.add(query);
        bmobQueries.add(historyBmobQuery);
        BmobQuery<Collection>totalQuery=new BmobQuery<Collection>();
        totalQuery.and(bmobQueries);
        totalQuery.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    Log.d("ObjiectNum", "" + list.size());
                    if (list.size() > 0) {
                        //存在收藏
                        mHistoryP.setQueryCode(1);
                    } else {
                        //添加收藏
                        updateCollection();
                        mHistoryP.setQueryCode(0);
                    }
                }
            }
        });
    }
}
