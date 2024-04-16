package com.jack.learn.designpattern.dynamicproxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 主要用于在运行时动态地创建一个对象的代理，以实现对原有对象的增强或控制
 * 优点：解耦
 *
 * 在内存中生成字节码  $Proxy0
 * 1.怎么在内存中生成？
 *  生成字节码文件  生成Class对象
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    /**
     * proxy 代表的是代理对象
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
