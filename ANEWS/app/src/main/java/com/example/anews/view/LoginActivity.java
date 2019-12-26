package com.example.anews.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anews.Constant;
import com.example.anews.R;
import com.example.anews.presenter.ILoginP;
import com.example.anews.presenter.LoginP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ILoginV {
    ILoginP mILoginP;
    private Dialog fDialog;
    private EditText loginPhoneET;
    private EditText loginPasswordET;
    private EditText registerPhoneET;
    private EditText registerPasswordET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Bmob.initialize(this, Constant.BmobKey);

        findViewById(R.id.login_login_btn).setOnClickListener(this);
        findViewById(R.id.login_register_btn).setOnClickListener(this);

        loginPhoneET = findViewById(R.id.login_user_name_et);
        loginPasswordET = findViewById(R.id.login_password_et);
        //检查是否有本地存储过的用户数据
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        String phoneNum = sharedPreferences.getString("phone",null);
        String password = sharedPreferences.getString("password",null);

        if(phoneNum!=null&&password!=null){
            loginPhoneET.setText(phoneNum);
            loginPasswordET.setText(password);
        }

        mILoginP = new LoginP(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login_btn:
                login();
                break;
            case R.id.login_register_btn:
                showRegister();
                break;
            case R.id.register_register_btn:
                register();
                break;
            case R.id.register_cancel_btn:
                fDialog.cancel();
                break;
        }
    }
//显示注册界面
    private void showRegister() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.register, null);//引用注册布局

        myView.findViewById(R.id.register_register_btn).setOnClickListener(this);
        myView.findViewById(R.id.register_cancel_btn).setOnClickListener(this);
        registerPhoneET = myView.findViewById(R.id.register_user_name_et);
        registerPasswordET = myView.findViewById(R.id.register_password_et);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(myView);
        fDialog = builder.create();//创建对话框
        fDialog.show();//显示对话框
    }
//注册
    private void register() {
        String phone = registerPhoneET.getText().toString();
        //使用正则表达式验证手机号
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        if(!m.matches()) {
            Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show();
            return;
        }
        mILoginP.doRegister(phone, registerPasswordET.getText().toString());
    }
//登录信息获取
    private void login() {
        String phone = loginPhoneET.getText().toString();
        //使用正则表达式验证手机号
        String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phone);
        if(!m.matches()) {
            Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show();
            return;
        }
        mILoginP.doLogin(phone, loginPasswordET.getText().toString());
    }
//登录结果
    @Override
    public void onLoginResult(int resultCode) {
        if(resultCode == 1) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            //登录信息的本地化存储
            SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
            editor.clear();
            editor.putString("phone",loginPhoneET.getText().toString());
            editor.putString("password",loginPasswordET.getText().toString());
            editor.commit();
            //跳转至新闻主界面
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if(resultCode == 0)
            Toast.makeText(this,"登录失败", Toast.LENGTH_SHORT).show();
    }
//注册结果
    @Override
    public void onRegisterResult(int resultCode) {
        //返回码 1：注册成功
        //返回码 0：注册失败
        if(resultCode == 1) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            //回填注册信息
            loginPhoneET.setText(registerPhoneET.getText().toString());
            loginPasswordET.setText(registerPasswordET.getText().toString());
            fDialog.dismiss();
        }
        else if(resultCode == 0)
            Toast.makeText(this,"注册失败:手机号重复", Toast.LENGTH_SHORT).show();
    }
}
