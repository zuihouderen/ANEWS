package com.example.anews.view.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.anews.R;
import com.example.anews.presenter.IMainP;
import com.example.anews.presenter.MainP;
import com.example.anews.view.IMainV;
import com.example.anews.view.NewsDetailActivity;
import com.example.anews.view.fragment.NewsItem;

import java.util.ArrayList;
import java.util.List;

//搜索页面
public class SearchActivity extends AppCompatActivity implements View.OnClickListener, IMainV {
    private IMainP mIMainP;

    private EditText mSearchText;
    private TextView mSearch;
    private TextView mHistoryRecord;
    private ListView mSearchHistory;
    private ListView mSearchDetail;

    private List<String>mSearchKeyword = new ArrayList<String>();
    private List<NewsItem>mNewsItems;
    private static final String TAG = "SearchActivity";
    private ArrayAdapter<String> mHistoryAdapter;
    private SearchAdaptor mSearchAdaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        mIMainP = new MainP(this);
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("新闻搜索");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //控件初始化及事件监听注册
        initWidget();
        //加载搜索历史
        initSearchHistory();
    }

    private void initWidget(){
        mSearchText = findViewById(R.id.search_edittext);
        mSearch = findViewById(R.id.search_search);
        mHistoryRecord = findViewById(R.id.search_record);
        mSearchHistory = findViewById(R.id.search_history_listview);
        mSearchDetail = findViewById(R.id.search_detail_listview);

        mSearch.setOnClickListener(this);
        mSearchText.setOnClickListener(this);
        mHistoryRecord.setOnClickListener(this);

        mHistoryAdapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,mSearchKeyword);
        mSearchHistory.setAdapter(mHistoryAdapter);
        mSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = mSearchKeyword.get(position);
                mSearchText.setText(str);
                mSearchKeyword.remove(position);
                mSearchKeyword.add(0,str);
                addSearchHistory(str);
                mHistoryAdapter.notifyDataSetChanged();
            }
        });

        //点击搜索结果项查看新闻
        mSearchDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String>data = new ArrayList<>();
                data.add(mNewsItems.get(position).getmTitle());
                data.add(mNewsItems.get(position).getmPicURL());
                data.add(mNewsItems.get(position).getmContent());
                Intent intent = new Intent(SearchActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //加载本地搜索记录
    private void initSearchHistory(){
        SharedPreferences sharedPreferences = getSharedPreferences("search",MODE_PRIVATE);
        int num = sharedPreferences.getInt("number",-1);
        if(num == -1)
            return;
        for(int i=0;i < num;i++){
            String key = sharedPreferences.getString("key"+i,null);
            mSearchKeyword.add(key);
        }
        mHistoryAdapter.notifyDataSetChanged();;
    }
    //搜索记录本地存储
    private void addSearchHistory(String key){
        if(!mSearchKeyword.contains(key)) {
            mSearchKeyword.add(0, key);
            mHistoryAdapter.notifyDataSetChanged();
        }
        if(mSearchKeyword.size() == 11){
            mSearchKeyword.remove(10);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("search",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("number",mSearchKeyword.size());
        for(int i = 0;i < mSearchKeyword.size();i++){
            editor.putString("key"+i,mSearchKeyword.get(i));
        }
        editor.commit();
    }

    private void search() {
        String keyword = mSearchText.getText().toString();
        if(!keyword.equals("")){
            Log.d(TAG, "search: " + keyword);
            addSearchHistory(keyword);
            mSearchHistory.setVisibility(View.GONE);
            mHistoryRecord.setVisibility(View.GONE);
            mIMainP.doSearchNews(keyword);
        }
        else
            Log.d(TAG, "search: 文本框为空");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_search :
                search();
                break;
            case R.id.search_edittext:
                if(mSearchHistory.getVisibility() == View.GONE) {
                    mSearchHistory.setVisibility(View.VISIBLE);
                    mHistoryRecord.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.search_record:
                mSearchHistory.setVisibility(View.GONE);
                mHistoryRecord.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResult(String type, List<NewsItem> newsItems) {
        mNewsItems = newsItems;
        mSearchAdaptor = new SearchAdaptor(SearchActivity.this,mNewsItems);
        mSearchDetail.setAdapter(mSearchAdaptor);
    }

    @Override
    public void onRefresh(List<NewsItem> newsItems) {

    }

    @Override
    public void onLoadMore(List<NewsItem> newsItems) {

    }
}
