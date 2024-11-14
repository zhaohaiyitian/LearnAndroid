package com.jack.learn.kotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jack.learn.R
import com.jack.learn.click
import com.jack.learn.databinding.ActivityKotlinBinding
import com.jack.learn.jetpack.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * kotlin代码本质上也是通过kotlin编译器编译后生成VM（虚拟机）能识别的字节码
 *
 * 协程
 * 协程由程序自己创建和调度，不需要操作系统调度，所以协程比线程更加轻量。
 * 协程不能独立存在，必须依赖于线程
 * 以同步方式 写异步代码
 *
 * suspend 函数相比于普通函数内部多了一个 Continuation 续体实例（Kotlin转成Java代码之后即可看到），suspend函数中可以调用普通函数，但普通函数却不能调用suspend函数
 * 当线程执行到协程的 suspend 函数的时候，暂时不继续执行协程代码了。这个挂起，是针对当前线程来说的，从当前线程挂起，就是这个协程从执行它的线程上脱离，并不是说协程停下来了，
 * 而是当前线程不再管这个协程要去做什么了。
 * 当协程执行到挂起函数时，从当前线程脱离，然后继续执行，这个时候在哪个线程执行，由协程调度器所指定，挂起函数执行完之后，又会重新切回到它原先的线程来。这个就是协程的优势所在。
 *
 * 挂起函数并不一定会导致协程挂起，只有在发生异步调用时才会挂起
 *
 * 协程就是通过传递 Continuation 来控制异步调用流程的：将函数挂起之后执行的逻辑包装起成一个Continuation, 里面包含了挂起点信息，这样当协程恢复时就可以在挂起点继续执行。
 *
 * suspend挂起函数的执行流程就是通过CPS变换 + Continuation + 状态机来运转的
 *
 * lifecycleScope viewModelScope内部可以在合适的时机自动关闭协程，从而避免内存泄露的发生
 */
class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        viewBinding.apply {
            button.click {
                mViewModel.startTimer()

//                lifecycleScope.launch {
//                    val job1 = launch {
//                        delay(1000)
//                        Log.d("wangjie","job1")
//                    }
//                    launch(SupervisorJob(coroutineContext[Job])) {
//                        val supervisorJob1 = launch {
//                            delay(1000)
//                            throw NullPointerException()
//                        }
//                        val supervisorJob2 = launch {
//                            delay(1000)
//                            Log.d("wangjie","supervisorJob2")
//                        }
//                    }
//                }

//                lifecycleScope.launch {
//                    try {
//                        coroutineScope {
//                            val job1 = launch {
//                                delay(2000)
//                                // 如果抛出异常 ， job1 停止执行 ， job2 也会被取消，停止执行
//                                throw NullPointerException()
//                            }
//
//                            val job2 = launch {
//                                delay(2000)
//                                println("println coroutineScope job 2")
//                            }
//                            // 如果执行 cancel ,job 1 ， job 2 均取消
//                            // cancel()
//                        }
//                    } catch (e: Throwable) {
//                        // ignore
//                    }
//
//                    try {
//                        supervisorScope {
//                            val job1 = launch {
//                                delay(2000)
//                                // 如果抛出异常 ， job1 停止执行 ， job2 继续执行
//                                throw NullPointerException()
//                            }
//
//                            val job2 = launch {
//                                delay(2000)
//                                println("println supervisorScope job 2")
//                            }
//                            // 如果执行 cancel ,job 1 ， job 2 均取消
//                            // cancel()
//                        }
//                    } catch (e: Throwable) {
//                        // ignore
//                    }
//                }
            }
            lifecycleScope.launch {
                mViewModel.stateFlow.collect {
                    textView.text = it.toString()
                    Log.d("FlowTest", "Update time $it in UI.")
                }
            }
        }
//        Person("yang",18)
//        test()
//        testScope()
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
        // 返回的是函数块最后一行的值
        with("str") {
            this.length
        }
        // 返回值 是该对象
        "str".also {
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

            val asyncDeffed1 = async { }
            val asyncDeffed2 = async { }
            val asyncDeffed3 = async { }
            val asyncDeffed4 = async { }
            val result = asyncDeffed1.await()
        }
        myDispatcher.close()
        job.cancel() // 协程被取消时会抛出 CancellationException
    }


    /**
     * 如何处理协程并发的数据安全
     * 1.使用原子类 AtomicXXX
     * 2.channel channel要设置队列容量 channel.send() channel.receive()
     * 3.mutex 使用互斥锁
     */
    private fun testSafeCoroutine() {
        runBlocking {
            var count = 0
            val mutex = Mutex()
            List(1000) {
                CoroutineScope(Dispatchers.IO).launch {
                    mutex.withLock {
                        count++
                    }

//                    mutex.lock()
//                    try {
//                       mutex.lock()
//                        count++
//                    } finally {
//                        mutex.unlock()
//                    }
                }
            }.joinAll()
            Log.d("wangjie", "result: $count")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}