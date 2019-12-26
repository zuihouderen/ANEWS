package com.example.anews.model;

import android.util.Log;

import com.example.anews.bmob.History;
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

//对历史记录做云数据库修改的类
public class HistoryM implements IHistoryM {
    private List<HistoryItem> mHistoryItems;
    private BmobUser bmobUser;
    private String url;
    private String title;
    private String channel;
    private String content;
    private HistoryP mHistoryP;
    private RecommendP recommendP;


    public HistoryM(RecommendP recommendP) {
        this.recommendP=recommendP;
        this.bmobUser = BmobUser.getCurrentUser(BmobUser.class);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBmobUser(BmobUser bmobUser) {
        this.bmobUser = bmobUser;
    }

    public HistoryM(HistoryP historyP){
        this.mHistoryP =historyP;
        this.bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        this.mHistoryItems = new ArrayList<>();
    }

    /**
     * 插入历史记录，更新数据库
     */
    @Override
    public void updateHistory() {
        History history=new History();
        history.setAuthor(bmobUser);
        history.setTitle(title);
        history.setUrl(url);
        history.setChannel(channel);
        history.setContent(content);
        history.setCount(1);
        history.save(new SaveListener< String >() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.d("SUCCESS:","插入历史记录成功");
                }
                else{
                    Log.e("fail",e.toString());
                }
            }
        });
    }



    /**
     * 点击一条新闻，更新count
     */
    @Override
    public void queryAndUpdate(){

        BmobQuery<History>query=new BmobQuery<History>();
        query.addWhereEqualTo("author",bmobUser);
        BmobQuery<History> historyBmobQuery=new BmobQuery<History>();
        historyBmobQuery.addWhereEqualTo("url",url);
        List<BmobQuery<History>> bmobQueries=new ArrayList<BmobQuery< History>>();
        bmobQueries.add(query);
        bmobQueries.add(historyBmobQuery);
        BmobQuery<History>totalQuery=new BmobQuery<History>();
        totalQuery.and(bmobQueries);
        totalQuery.findObjects(new FindListener< History >() {
            @Override
            public void done(List< History > list, BmobException e) {
                if (e == null) {
                    Log.d("ObjiectNum",""+list.size());
                    if(list.size()>0){
//                        for(int i=0;i<list.size();i++)
//                            Log.d("value",list.get(i).getAuthor().getObjectId());
                        for(int i=0;i<list.size();i++) {
                            int count =list.get(i).getCount().intValue();
                            count++;
                            updateCount(list.get(i),count);
                        }
                    }
                    else {
                        updateHistory();
                    }

                } else {

                    Log.e("BMOB", e.toString());

                }
        }
        });
    }

    /**
     * 更新一条新闻的权值
     * @param history
     * @param count
     */
    public void updateCount(History history, final int count){
        history.setCount(count);
        history.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("update success",""+count);
                }
                else {
                    Log.e("update fail","更新失败权值");
                }
            }
        });
    }




    /**
     * 查找同一个用户权值最大的的新闻Channel
     */
    @Override
    public void queryMax(){
        final List<String>strings=new ArrayList<>();
        BmobQuery<History>query=new BmobQuery<History>();
        query.addWhereEqualTo("author",bmobUser);
        query.order("-count");
        query.setLimit(10);
        query.findObjects(new FindListener< History >() {
            @Override
            public void done(List< History > list, BmobException e) {
                if(e==null){
                    Log.d("历史记录查询成功",""+list.size());
                    for(int i=0;i<list.size();i++) {
                      String res=list.get(i).getChannel();
                      strings.add(res);
                      Log.d("TAG",""+list.get(i).getTitle());
                    }
                    /**
                     * 获取历史频道
                     */
                    recommendP.setHChannel(strings);
                   // Log.d("内部的SIZE",strings.size()+"");
                }
                else {
                    Log.e("update fail","查询最大权值新闻失败"+e);
                }
            }
        });
    }



    /**
     * 查找同一个用户观看过的新闻(10)
     */
    public void queryHistory(){
        final List<History>result=new ArrayList<>();
        BmobQuery<History>query=new BmobQuery<History>();
        query.addWhereEqualTo("author",bmobUser);
        query.order("-updatedAt");
        query.setLimit(10);
        query.findObjects(new FindListener< History >() {
            @Override
            public void done(List< History > list, BmobException e) {
                if(e==null){
                    Log.d("查询成功",""+list.size());
                    for(int i=0;i<list.size();i++) {
                        History history=new History();
                        String picURL = list.get(i).getUrl();
                        String content = list.get(i).getContent();
                        String title = list.get(i).getTitle();
                        String objectID = list.get(i).getObjectId();

                        history.setUrl(picURL);
                        history.setContent(list.get(i).getContent());
                        history.setTitle(list.get(i).getTitle());
                        history.setChannel(list.get(i).getChannel());
                        history.setAuthor(list.get(i).getAuthor());
                        history.setCount(list.get(i).getCount());
                        result.add(history);
                        HistoryItem historyItem = new HistoryItem(picURL,title,content,objectID);
                        mHistoryItems.add(historyItem);
                        /**
                         * 在这里获取所有需要的收藏记录
                         */
                    }
                    mHistoryP.setNewsItems(mHistoryItems);
                }
                else {
                    Log.e("查询失败","获取历史记录失败");
                }
            }
        });
    }
}
