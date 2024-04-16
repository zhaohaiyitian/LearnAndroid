package com.jack.learn.designpattern.staticproxy;

import android.util.Log;


/**
 * 真正提供服务的类
 */
public class AaFactory implements ManToolsFactory {
    @Override
    public void saleManTools(int size) {
        Log.d("wangjie","根据你的需求为你提供了"+size+"的模特");
    }
}
