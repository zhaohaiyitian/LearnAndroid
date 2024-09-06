package com.jack.learn.crash

import android.content.Context
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

    /**
     * SharedPreferences是一种轻量级的数据存储方式，用于存储键值对形式的数据 不支持多进程
     * 初始化：子线程使用JavaIO读取整个文件，进行XML解析，存入Map集合，对于SP的其他操作都需要等待初始化完成
     *
     * 全量更新（重新序列化，并覆盖文件实现更新）
     * IO 用mmap优化
     * dom解析  通过protobuf优化
     */
    private fun testSP() {
        val sp = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        sp.getString("","")
        val editor = sp.edit()
        editor.putString("","")
        editor.commit() // 同步提交 有返回值 阻塞调用线程 可能导致ANR
        editor.apply() // 异步提交 无返回值
    }

}