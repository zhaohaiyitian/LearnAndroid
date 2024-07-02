package com.jack.learn.architecture.mvp.login;

import com.jack.learn.architecture.mvp.User;

/**
 * 契约，顾名思义，规范定义，定义功能和模板
 */
public class LoginContract {
    public interface LoginView {
        void onCheckFormatSuccess();

        void onCheckFormatFail(String info);

        void onLoginSuccess(User user);

        void onLoginFail(String errorInfo);

    }
    public interface LoginModel {
        void login(String name,String password,LoginCallBack callBack);
    }

    public interface LoginCallBack {
        void onSuccess(User user);

        void onFail(String errorInfo);
    }
}
