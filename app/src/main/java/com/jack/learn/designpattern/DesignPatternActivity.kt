package com.jack.learn.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.R
import com.jack.learn.designpattern.proxy.MyInvocationHandler
import com.jack.learn.designpattern.proxy.RealSubject
import com.jack.learn.designpattern.proxy.Subject
import java.lang.reflect.Proxy

class DesignPatternActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_pattern)
        val realSubject = RealSubject()
        val handler = MyInvocationHandler(realSubject)

        /**
         * 第一个参数: 类加载器用哪个类加载器去加载动态生成的子类
         * 第二个参数：类所实现的接口
         * 第三个参数：让子类去调用方法的时候，需要执行的内容
         */
        val proxySubject = Proxy.newProxyInstance(realSubject.javaClass.classLoader,realSubject.javaClass.interfaces,handler) as Subject
        proxySubject.doSomething("动态代理")
    }
}