package com.jack.learn.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jack.learn.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        Person("yang",18)
        test()
        testScope()
    }

    fun test() {
        // block: T.() -> R 是一个带有接收者的函数类型  返回的是lambda表达式的执行结果
        "wangjie".run {
            length
        }
        // block: T.() -> Unit 返回对象是自己
        Person("",18).apply {
            nameT
        }
        // block: (T) -> R 普通的函数类型  返回的是lambda表达式的执行结果
        "wangjie".let {
            it.length
        }
    }

    private fun testScope() {
        // 指定协程运行在我们自己的线程上
        val myDispatcher = Executors.newSingleThreadExecutor { r-> Thread(r,"MyThread") }.asCoroutineDispatcher()
        val job = GlobalScope.launch(myDispatcher) {
            if (isActive) {

            }
            Log.d("wangjie", "目标线程：" + Thread.currentThread().name)

            val asyncDeffed = async { }
            val result = asyncDeffed.await()
        }
        myDispatcher.close()
        job.cancel() // 协程被取消时会抛出 CancellationException




    }

    override fun onDestroy() {
        super.onDestroy()

    }
}