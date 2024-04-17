package com.jack.learn.designpattern.sample

import android.util.Log
import android.view.View
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class ClickHandlerProxy(private val target: View.OnClickListener):InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any?>?): Any? {
        if (method.name == "onClick") {
            Log.d("wangjie","Click event intercepted.")
            // 在事件处理前可以执行自定义逻辑
        }
        return method.invoke(target,*args.orEmpty())
    }
}