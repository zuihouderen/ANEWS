package com.example.anews.view.history;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.anews.R;
import com.example.anews.presenter.HistoryP;
import com.example.anews.presenter.IHistoryP;
import com.example.anews.view.IHistoryV;
import com.example.anews.view.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

//查看收藏和历史记录的页面
public class HistoryActivity extends AppCompatActivity implements IHistoryV {
    private IHistoryP mIHistoryP;
    private ListView mHistoryListView;
    private HistoryAdaptor mHistoryLVAdaptor;
    private List<HistoryItem> mHistoryItems;
    private String mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_view);
        mIHistoryP = new HistoryP(this);
        Toolbar toolbar = findViewById(R.id.history_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initWidget();
        Intent intent = getIntent();
        mType = intent.getStringExtra("type");
        Log.d("select type", "onCreate: "+mType);
        if(mType.equals("历史") ) {
            toolbar.setTitle("浏览历史");
            initHistory();
        }
        else if(mType.equals("收藏") ){
            toolbar.setTitle("我的收藏");
            initCollection();
        }
    }

    private void initCollection() {
        mIHistoryP.doQueryCollection();
        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> data = new ArrayList<>();
                data.add(mHistoryItems.get(position).getmTitle());
                data.add(mHistoryItems.get(position).getmPicURL());
                data.add(mHistoryItems.get(position).getmContent());
                Intent intent = new Intent(HistoryActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mHistoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HistoryActivity.this).setIcon(R.mipmap.ic_launcher).setTitle("选项")
                        .setMessage("确定取消收藏？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mIHistoryP.cancelCollection(mHistoryItems.get(position).getmObjectID());
                                mHistoryItems.remove(position);
                                mHistoryLVAdaptor.notifyDataSetChanged();
                                Toast.makeText(HistoryActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(HistoryActivity.this, "取消", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
                return true;
            }
        });
    }

    private void initWidget() {
        mHistoryListView = findViewById(R.id.history_listview);
    }

    private void initHistory() {
        mIHistoryP.doQueryHistory();
        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> data = new ArrayList<>();
                data.add(mHistoryItems.get(position).getmTitle());
                data.add(mHistoryItems.get(position).getmPicURL());
                data.add(mHistoryItems.get(position).getmContent());
                Intent intent = new Intent(HistoryActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAskRecordResult(List<HistoryItem> newsItems) {
        this.mHistoryItems = newsItems;
        mHistoryLVAdaptor = new HistoryAdaptor(HistoryActivity.this, mType, mHistoryItems);
        mHistoryListView.setAdapter(mHistoryLVAdaptor);
    }

    @Override
    public void onQueryCollectionResult(int resultCode) {
        if(resultCode == 1)
            Toast.makeText(HistoryActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
        if(resultCode == 0)
            Toast.makeText(HistoryActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
    }
}
