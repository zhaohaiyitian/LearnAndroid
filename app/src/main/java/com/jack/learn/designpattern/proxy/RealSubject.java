package com.jack.learn.designpattern.proxy;

import android.util.Log;

public class RealSubject implements Subject {
    @Override
    public String doSomething(String str) {
        Log.d("wangjie","RealSubject doSomething: "+str);
        return str;
    }
}
