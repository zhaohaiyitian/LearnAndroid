package com.wj.wjnews.net.http.client;

import okhttp3.Response;

/**
 * 请求接口的回调
 * Created by wj on 18-5-6.
 */

public interface CallBack<T> {
    void onSuccess(com.wj.wjnews.net.http.Response<T> response);
    void onFailure(String errorMsg);

}
