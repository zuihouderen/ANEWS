package com.example.anews.presenter;

public interface ILoginP {
    void doLogin(String phoneNum, String password);

    void doRegister(String phoneNum, String password);

    void setLoginResultCode(int resultCode);

    void setRegisterResultCode(int resultCode);
}
