package com.jack.learn.crash

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.R
import java.lang.ref.WeakReference

class MemoryActivity : AppCompatActivity() {

    // This `Handler` class should be static or leaks might occur (anonymous android.os.Handler)
    // 非静态内部类 持有外部类的引用 因为把被延迟的消息加入到了主线程的消息队列中，消息中有包涵了Handler的引用 匿名内部类的Handler又持有外部类Activity的引用
    // 将导致Activity无法回收以及Activity持有的很多资源也无法回收，这就引起了内存泄露。
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)
        // 模拟内存泄露，延迟5分钟发送消息
        handler.postDelayed({

        },5000)

        // 解决了内存泄露
        val myHandler = MyHandler(this)
        myHandler.postDelayed(runnable, 5000)
    }


    private val runnable = Runnable {
        // 执行业务逻辑
    }
    // 静态内部类+弱引用 解决Handler的内存泄露
    private class MyHandler(activity: MemoryActivity) : Handler() {
        // 持有弱引用MemoryActivity，GC回收时会被回收掉
        private val mActivity: WeakReference<MemoryActivity>
        init {
            mActivity = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val pluginContainerActivity = mActivity.get()
            if (pluginContainerActivity != null) {
                // 执行业务逻辑
            }
        }
    }

}