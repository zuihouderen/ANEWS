package com.example.anews.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.anews.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

//新闻详情页
public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        ImageView imageView = findViewById(R.id.news_detail_image);
        TextView title = findViewById(R.id.news_detail_title);
        HtmlTextView htmlTextView = findViewById(R.id.news_detail_htmltextview);
        Toolbar toolbar = findViewById(R.id.news_detail_toolbar);

        toolbar.setTitle("新闻详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        ArrayList<String>data = bundle.getStringArrayList("data");
        //0. title   1. picURL   2.content

        title.setText(data.get(0));
        String picURL = data.get(1);
        Glide.with(this)
                .load(picURL)
                .into(imageView);
        String content = data.get(2);

        htmlTextView.setHtml(content,new HtmlHttpImageGetter(htmlTextView));
    }
}
