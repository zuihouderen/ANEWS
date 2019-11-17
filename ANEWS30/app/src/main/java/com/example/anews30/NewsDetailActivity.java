package com.example.anews30;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar) ;
        WebView webView=(WebView) findViewById(R.id.web_text);
        toolbar.setTitle("新闻详情");
        ImageView imageView=findViewById(R.id.ivImage);

        //setSupportActionBar(toolbar);
//        设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final ArrayList<String> data = bundle.getStringArrayList("data");
        Log.d("url", data.get(0).toString());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(data.get(1));

        Glide.with(this)
                .load(data.get(0)).error(R.mipmap.ic_launcher)
                .fitCenter().into(imageView);

    }
}
