package com.jack.learn.designpattern

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jack.learn.R
import com.jack.learn.databinding.ActivityDesignPatternBinding
import com.jack.learn.designpattern.dynamicproxy.MyInvocationHandler
import com.jack.learn.designpattern.dynamicproxy.RealSubject
import com.jack.learn.designpattern.dynamicproxy.Subject
import com.jack.learn.designpattern.sample.ClickHandlerProxy
import com.jack.learn.designpattern.staticproxy.AaFactory
import com.jack.learn.designpattern.staticproxy.Jack
import java.lang.reflect.Proxy

class DesignPatternActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityDesignPatternBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        val realSubject = RealSubject()
        val handler = MyInvocationHandler(realSubject)

        /**
         * 第一个参数: 类加载器用哪个类加载器去加载动态生成的子类
         * 第二个参数：类所实现的接口
         * 第三个参数：让子类去调用方法的时候，需要执行的内容
         */
        // Proxy：调度器
        val proxySubject = Proxy.newProxyInstance(realSubject.javaClass.classLoader,realSubject.javaClass.interfaces,handler) as Subject
        proxySubject.doSomething("动态代理")
//        testStaticProxy()
        viewBinding.apply {
            sample(button)
        }
    }

    private fun sample(view: View) {
        val originalClickListener = View.OnClickListener {
            // 原始的点击事件处理逻辑
            Log.d("wangjie","originalClickListener")
        }
        val proxyClickListener = Proxy.newProxyInstance(
            originalClickListener.javaClass.classLoader,
            originalClickListener.javaClass.interfaces,
            ClickHandlerProxy(originalClickListener)) as View.OnClickListener
        view.setOnClickListener(proxyClickListener)
    }

    // 客户方法
    private fun testStaticProxy() {
        // 生产娃娃的工厂
        val factory = AaFactory()
        // 代理这个公司的产品
        val jack = Jack(factory)
        // 张三找到jack买了一个1型号的娃娃
        jack.saleManTools(1)
    }
}