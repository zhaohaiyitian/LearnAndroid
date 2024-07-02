package com.jack.learn.architecture.mvp.login;

import android.os.Bundle;

import com.jack.learn.R;
import com.jack.learn.architecture.mvp.User;
import com.jack.learn.architecture.mvp.base.BaseMVPActivity;

public class LoginActivity extends BaseMVPActivity<LoginContract.LoginView,LoginPresenter> implements LoginContract.LoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mPresenter.login();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onCheckFormatSuccess() {

    }

    @Override
    public void onCheckFormatFail(String info) {

    }

    @Override
    public void onLoginSuccess(User user) {

    }

    @Override
    public void onLoginFail(String errorInfo) {

    }
}