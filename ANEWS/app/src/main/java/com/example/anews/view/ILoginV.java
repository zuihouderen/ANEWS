package com.example.anews.view;

//登录界面使用的接口
public interface ILoginV {
    //获取登录结果，并根据结果改变UI
    void onLoginResult(int resultCode);
    //获取注册结果，并根据结果改变UI
    void onRegisterResult(int resultCode);
}
