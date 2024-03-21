package com.jack.learn.designpattern.proxy;


/**
 * 动态代理： 动态生成接口的子类
 *
 * 需要提供接口，接口的类路径以及接口中的方法名，方法需要执行的内容
 */
public interface Subject {
    String doSomething(String str);
}
