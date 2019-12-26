package com.example.anews.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anews.R;
import com.example.anews.presenter.IMainP;
import com.example.anews.presenter.MainP;
import com.example.anews.view.fragment.NewsFragment;
import com.example.anews.view.fragment.NewsItem;
import com.example.anews.view.history.HistoryActivity;
import com.example.anews.view.search.SearchActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainV, SensorEventListener {
    public static final int RecommendIndex = 0;
    public static final int FinanceIndex = 1;
    public static final int SportIndex = 2;
    public static final int EntertainmentIndex = 3;
    public static final int MilitaryIndex = 4;
    public static final int EducationIndex = 5;
    public static final int TechnicIndex = 6;
    private IMainP mIMainP;

    private TextView recommend;
    private TextView finance;
    private TextView sport;
    private TextView entertainment;
    private TextView military;
    private TextView education;
    private TextView technic;
    private Toolbar mToolbar;

    private List<String>mNewsType;
    private List<NewsFragment>mFragments;
    private List<NewsItem>mNewsItems;
    public static final int START = 0;
    public static final int NUM = 20;
    private int fragmentIndex = 0;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private static final String TAG = "MainActivity";
    private AlertDialog mDialog;
    private EditText mFirstPassword;
    private EditText mSecondPassword;

    private SensorManager mSensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIMainP = new MainP(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //初始化新闻频道标签
        initTextView();
        //初始化顶部工具栏
        initToolBar();
        //初始化新闻fragment
        initFragment();
        //初始化左侧导航栏
        initNavigation();
    }
//频道切换初始化
    private void initTextView(){
        recommend = findViewById(R.id.recommend);
        finance = findViewById(R.id.finance);
        sport = findViewById(R.id.sport);
        entertainment = findViewById(R.id.entertainment);
        military = findViewById(R.id.military);
        education = findViewById(R.id.education);
        technic = findViewById(R.id.technic);

        recommend.setOnClickListener(this);
        finance.setOnClickListener(this);
        sport.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        military.setOnClickListener(this);
        education.setOnClickListener(this);
        technic.setOnClickListener(this);
    }
//顶部工具栏初始化
    private void initToolBar(){
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //todo 完成菜单点击逻辑
                switch (item.getItemId()){
                    case R.id.menu_item_search:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        Toast.makeText(MainActivity.this, "search",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
//Fragment初始化
    private void initFragment(){
        Log.d("try to build fragment", "initFragment: ");
        mFragments = new ArrayList<>();

        mNewsType = new ArrayList<>();
        mNewsType.add("头条");
        mNewsType.add("财经");
        mNewsType.add("体育");
        mNewsType.add("娱乐");
        mNewsType.add("军事");
        mNewsType.add("教育");
        mNewsType.add("科技");
        for (String item:mNewsType) {
            Log.d("news type", "initFragment: " + item);
            mIMainP.doGetNews(item,START,NUM);
        }
        recommend.setTextColor(getColor(R.color.colorAccent));
    }
//左侧导航初始化
    private void initNavigation(){
        final BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
        TextView textView;
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        //设置用户名
        textView = header.findViewById(R.id.navigation_user_name);
        textView.setText("用户"+user.getUsername());
        //设置各项点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.nav_collect:
                        Toast.makeText(MainActivity.this, "收藏",Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this, HistoryActivity.class);
                        intent.putExtra("type","收藏");
                        startActivity(intent);
                        break;
                    case R.id.nav_history:
                        Toast.makeText(MainActivity.this, "历史",Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this, HistoryActivity.class);
                        intent.putExtra("type","历史");
                        startActivity(intent);
                        break;
                    case R.id.nav_change_password:
                        Toast.makeText(MainActivity.this, "修改密码",Toast.LENGTH_SHORT).show();
                        changePassword();
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "注销",Toast.LENGTH_SHORT).show();
                        BmobUser.logOut();
                        intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                        break;
                }

                return true;
            }
        });
    }
//导航栏中修改密码的处理函数
    private void changePassword() {
        final BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.cahnge_password, null);//引用修改页面布局
        myView.findViewById(R.id.changed_password_change_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断两次密码输入是否一致
                if (mFirstPassword.getText().toString().equals(mSecondPassword.getText().toString())) {
                    Log.d(TAG, "onClick: 修改密码");
                    user.setPassword(mSecondPassword.getText().toString());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null)
                                Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });
                }
                else
                    Toast.makeText(MainActivity.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
            }
        });
        myView.findViewById(R.id.change_password_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mFirstPassword = myView.findViewById(R.id.before_password_et);
        mSecondPassword = myView.findViewById(R.id.changed_password_et);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(myView);
        mDialog = builder.create();//创建对话框
        mDialog.show();//显示对话框
    }
//新闻频道的切换
    private void fragmentChange(NewsFragment from, NewsFragment to){
        //mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(!to.isAdded()){
            mFragmentTransaction.hide(from).add(R.id.fgContainer, to);
        }else{
            Log.d(TAG, "fragmentChange: " + from.getNewsType() + " to "+to.getNewsType());
            mFragmentTransaction.hide(from).show(to);
        }
        mFragmentTransaction.commit();
    }
//修改频道时频道标签颜色改变
    private void textViewColorChange(int from, int to){
        switch (from){
            case FinanceIndex:
                finance.setTextColor(getColor(R.color.dimgrey));
                break;
            case SportIndex:
                sport.setTextColor(getColor(R.color.dimgrey));
                break;
            case EntertainmentIndex:
                entertainment.setTextColor(getColor(R.color.dimgrey));
                break;
            case MilitaryIndex:
                military.setTextColor(getColor(R.color.dimgrey));
                break;
            case EducationIndex:
                education.setTextColor(getColor(R.color.dimgrey));
                break;
            case TechnicIndex:
                technic.setTextColor(getColor(R.color.dimgrey));
                break;
            case RecommendIndex:
                recommend.setTextColor(getColor(R.color.dimgrey));
                break;
        }
        switch (to){
            case FinanceIndex:
                finance.setTextColor(getColor(R.color.colorAccent));
                break;
            case SportIndex:
                sport.setTextColor(getColor(R.color.colorAccent));
                break;
            case EntertainmentIndex:
                entertainment.setTextColor(getColor(R.color.colorAccent));
                break;
            case MilitaryIndex:
                military.setTextColor(getColor(R.color.colorAccent));
                break;
            case EducationIndex:
                education.setTextColor(getColor(R.color.colorAccent));
                break;
            case TechnicIndex:
                technic.setTextColor(getColor(R.color.colorAccent));
                break;
            case RecommendIndex:
                recommend.setTextColor(getColor(R.color.colorAccent));
                break;
        }
    }

    //点击新闻的频道标签时触发的切换事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recommend:
                //Toast.makeText(this,"recommend",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != RecommendIndex){
                    textViewColorChange(fragmentIndex, RecommendIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(RecommendIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(RecommendIndex).getNewsType());
                    fragmentIndex = RecommendIndex;
                }
                break;
            case R.id.finance:
                //Toast.makeText(this,"finance",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != FinanceIndex){
                    textViewColorChange(fragmentIndex, FinanceIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(FinanceIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(FinanceIndex).getNewsType());
                    fragmentIndex = FinanceIndex;
                }
                break;
            case R.id.sport:
                //Toast.makeText(this,"sport",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != SportIndex){
                    textViewColorChange(fragmentIndex, SportIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(SportIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(SportIndex).getNewsType());
                    fragmentIndex = SportIndex;
                }
                break;
            case R.id.entertainment:
                //Toast.makeText(this,"entertainment",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != EntertainmentIndex){
                    textViewColorChange(fragmentIndex, EntertainmentIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(EntertainmentIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(EntertainmentIndex).getNewsType());
                    fragmentIndex = EntertainmentIndex;
                }
                break;
            case R.id.military:
                //Toast.makeText(this,"military",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != MilitaryIndex){
                    textViewColorChange(fragmentIndex, MilitaryIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(MilitaryIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(MilitaryIndex).getNewsType());
                    fragmentIndex = MilitaryIndex;
                }
                break;
            case R.id.education:
                //Toast.makeText(this,"education",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != EducationIndex){
                    textViewColorChange(fragmentIndex, EducationIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(EducationIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(EducationIndex).getNewsType());
                    fragmentIndex = EducationIndex;
                }
                break;
            case R.id.technic:
                //Toast.makeText(this,"technic",Toast.LENGTH_SHORT).show();
                if(fragmentIndex != TechnicIndex){
                    textViewColorChange(fragmentIndex, TechnicIndex);
                    fragmentChange(mFragments.get(fragmentIndex),mFragments.get(TechnicIndex));
                    Log.d(TAG, "onClick: from "+mFragments.get(fragmentIndex).getNewsType()+" to "+mFragments.get(TechnicIndex).getNewsType());
                    fragmentIndex = TechnicIndex;
                }
                break;
        }
    }

    @Override
    public void onResult(String type, List<NewsItem> newsItems) {
        Log.i("number", "onAskRecordResult: " + newsItems.size() + type);
        this.mNewsItems = newsItems;

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        NewsFragment newsFragment = new NewsFragment(this,type,mNewsItems);
        mFragments.add(newsFragment);
        mFragmentTransaction.add(R.id.fgContainer, newsFragment);
        mFragmentTransaction.commit();
    }

    @Override
    public void onRefresh(List<NewsItem> newsItems) {

    }

    @Override
    public void onLoadMore(List<NewsItem> newsItems) {

    }


    //传感器方法
    protected void onResume() {
        super.onResume();
        //注册加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器TYPE类型
                SensorManager.SENSOR_DELAY_UI);//采集频率
    }
    /**
     * 暂停Activity，界面获取焦点，按钮可以点击时回调
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    //使用摇一摇
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[0];
        float z = event.values[0];
        if(x>10 || y>10 || z>10) {
            Toast.makeText(this, "欢迎使用摇一摇", Toast.LENGTH_SHORT).show();
            if(mFragments.get(fragmentIndex)!=null)
                mFragments.get(fragmentIndex).refresh();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
