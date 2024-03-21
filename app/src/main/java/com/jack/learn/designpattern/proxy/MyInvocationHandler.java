package com.jack.learn.designpattern.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    /**
     * proxy 代表的就是子类对象本身
     * method 代表的是子类正在调用的是哪个方法
     * args 代表子类调用自己方法 所传递的参数值
     * Object 代表 子类调用自己方法 的返回值
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("wangjie","Before.....");

        Object result = method.invoke(object,args);

        Log.d("wangjie","After.....");
        return result;
    }
}
