package com.example.anews.presenter;

import android.util.Log;

import com.example.anews.model.ILoginM;
import com.example.anews.model.LoginM;
import com.example.anews.view.ILoginV;

//处理登录和注册信息的类
public class LoginP implements ILoginP {
    ILoginV mILoginV;
    ILoginM mILoginM;
    private int mResultCode;

    public LoginP(ILoginV iLoginV){
        this.mILoginV = iLoginV;
        this.mResultCode = -1;
    }

    @Override
    public void doLogin(String name, String password) {
        mILoginM = new LoginM(this);
        mILoginM.checkUserInfo(name,password);
    }

    @Override
    public void doRegister(String name, String password) {
        mILoginM = new LoginM(this);
        mILoginM.registerUserInfo(name,password);
    }

    @Override
    public void setLoginResultCode(int resultCode) {
        this.mResultCode = resultCode;
        mILoginV.onLoginResult(mResultCode);
    }

    @Override
    public void setRegisterResultCode(int resultCode) {
        this.mResultCode = resultCode;
        mILoginV.onRegisterResult(mResultCode);
    }
}
