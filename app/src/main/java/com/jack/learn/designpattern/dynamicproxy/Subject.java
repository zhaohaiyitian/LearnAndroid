package com.jack.learn.designpattern.dynamicproxy;


/**
 * 真实对象和代理对象需要实现一个公共接口
 *
 *
 * 动态代理： 动态生成接口的子类
 * 需要提供接口，接口的类路径以及接口中的方法名，方法需要执行的内容
 */
public interface Subject {
    String doSomething(String str);
}
