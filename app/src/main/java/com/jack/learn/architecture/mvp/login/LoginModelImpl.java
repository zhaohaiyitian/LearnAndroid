package com.jack.learn.architecture.mvp.login;

import com.jack.learn.architecture.mvp.User;

public class LoginModelImpl implements LoginContract.LoginModel {
    @Override
    public void login(String name, String password, LoginContract.LoginCallBack callBack) {
        if (true) {
            callBack.onSuccess(new User());
        } else {
            callBack.onFail("");
        }
    }
}
