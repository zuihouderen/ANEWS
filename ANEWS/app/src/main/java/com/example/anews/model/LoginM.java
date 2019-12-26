package com.example.anews.model;

import android.util.Log;

import com.example.anews.presenter.ILoginP;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//对登录和注册进行云数据库查询和修改的类
public class LoginM implements ILoginM {
    ILoginP mILoginP;
    private int resultCode;

    public LoginM(ILoginP iLoginP){
        this.mILoginP = iLoginP;
        this.resultCode = -1;
    }

    //查询是否存在该用户
    @Override
    public void checkUserInfo(String username, String password) {
        final BmobUser user=new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    resultCode = 1;
                    mILoginP.setLoginResultCode(resultCode);
                    //saveComment(user);
                } else {
                    resultCode = 0;
                    mILoginP.setLoginResultCode(resultCode);
                    Log.d("loginm", "done: ");
                }
            }
        });
    }

    //注册用户信息
    @Override
    public void registerUserInfo(String name, String password) {
        BmobUser user = new BmobUser();
        user.setUsername(name );
        user.setPassword(password );
        user.signUp(new SaveListener< BmobUser >() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if(e==null){
                    Log.d("注册成功","happy");
                    resultCode = 1;
                    mILoginP.setRegisterResultCode(resultCode);
                }
                else{
                    resultCode = 0;
                    mILoginP.setRegisterResultCode(resultCode);
                    Log.e("注册失败","unhappy");
                }
            }
        });
    }
}
