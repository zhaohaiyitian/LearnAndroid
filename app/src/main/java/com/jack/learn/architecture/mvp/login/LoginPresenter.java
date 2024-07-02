package com.jack.learn.architecture.mvp.login;

import com.jack.learn.architecture.mvp.User;
import com.jack.learn.architecture.mvp.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> {

    private LoginModelImpl model;
    public LoginPresenter() {
        model = new LoginModelImpl();
    }

    public void login() {
        model.login("", "", new LoginContract.LoginCallBack() {
            @Override
            public void onSuccess(User user) {
                getView().onLoginSuccess(user);
            }

            @Override
            public void onFail(String errorInfo) {
                getView().onLoginFail(errorInfo);
            }
        });
    }
}
