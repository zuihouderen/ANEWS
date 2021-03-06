package com.example.anews.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

//表示历史记录信息的类
public class History extends BmobObject {
    private String url;
    private BmobUser author;
    private  String title;
    private Number count;
    private String channel;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }



    public BmobUser getAuthor() {
        return author;
    }

    public Number getCount() {
        return count;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(BmobUser author) {
        this.author = author;
    }

    public void setCount(Number count) {
        this.count = count;
    }
}
