package com.jack.learn.juc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pools.SimplePool
import com.jack.learn.aidl.Person
import com.jack.learn.click
import com.jack.learn.databinding.ActivityConcurrentBinding
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.Semaphore
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock


/**
 * 并发编程
 * AQS：AbstractQueuedSynchronizer
 */
class ConcurrentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityConcurrentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            button1.click {
                testReentrantLock()
            }
            button2.click {
                testCountDownLatch()
            }
            button3.click {
                testCyclicBarrier()
            }
            button4.click {
                testSemaphore()
            }
            button5.click {
//                testThreadPool()
                testQueue()
            }
        }
//        Thread.activeCount() // 监控线程数量
    }

    private fun testCOW() {
        val arrayList = CopyOnWriteArrayList<String>()
        arrayList.add("上海")
        arrayList.add("北京")
        arrayList.get(0)
        arrayList.remove("北京")
        arrayList.isEmpty()

        val arraySet = CopyOnWriteArraySet<String>()
        arraySet.add("上海")
        arraySet.add("北京")
        arraySet.remove("北京")

    }

    private fun testQueue() {
        val queue = ArrayBlockingQueue<String>(5) // 有界缓存区
        queue.add("历史")
        queue.add("地理")
        queue.offer("地理") // 添加
        queue.remove("历史")
        queue.poll()
        queue.peek()
        queue.take()

        val linkedQueue = LinkedBlockingQueue<String>(5) // 单向的阻塞队列 可以指定容量 如果未指定容量 则容量大小为LinkedBlockingQueue
        linkedQueue.add("历史")
        linkedQueue.add("地理")
        linkedQueue.offer("地理")
        linkedQueue.remove("历史")
        linkedQueue.poll()

        val deque = LinkedBlockingDeque<String>(5) // 双向的阻塞队列  可以在队列的头部和尾部进行插入和删除操作。
        deque.addFirst("")
        deque.addLast("")
        deque.putLast("")
        deque.putFirst("")
        deque.pollFirst()
        deque.pollLast()
        deque.removeLast()
        deque.removeFirst()
    }

    private fun testReentrantLock() {
        val lock = ReentrantLock(true)
        lock.lock()
        lock.unlock()
    }

    // 启动多个线程并发执行任务，等待所有线程执行完毕后进行结果汇总
    private fun testCountDownLatch() {
        val service = Executors.newFixedThreadPool(5)
        val latch = CountDownLatch(2)
        for (i in 0..2) {
            service.submit {
                println("task1 start...")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                latch.countDown()
            }
            service.submit {
                println("task2 start...")
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                latch.countDown()
            }
            try {
                latch.await()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            println("task1 task2 finish...")
        }
        service.shutdown()
    }

    // CyclicBarrier 字面意思回环栅栏（循环屏障），通过它可以实现让一组线程等待至某个状态（屏障点）之后再全部同时执行
    private fun testCyclicBarrier() {
        val service = Executors.newFixedThreadPool(3)
        val barrier = CyclicBarrier(2,{
            Log.d("wangjie","task1 task2 finish....")
        })
        for (i in 0..3) {
            service.submit {
                Log.d("wangjie","task1 finish....")
                Thread.sleep(1000)
                barrier.await()
                Log.d("wangjie","111111111111")
            }
            service.submit {
                Log.d("wangjie","task2 finish....")
                Thread.sleep(1000)
                barrier.await()
                Log.d("wangjie","2222222222222")
            }
        }
        service.shutdown()
    }

    //可以⽤来控制同时访问特定资源的线程数量
    private fun testSemaphore() {
        val exec = Executors.newCachedThreadPool()
        val phore = Semaphore(3)
        for (i in 0..4) {
            val task = Runnable {
                try {
                    // 获取许可
                    phore.acquire()
                    Log.d("wangjie","获得许可: $i")
                    // 休眠随机秒(表示正在执行操作)
                    TimeUnit.SECONDS.sleep((Math.random()*10+1).toLong())
                    // 访问完后，释放许可
                    phore.release()
                    // availablePermits()指还剩多少个许可
                    Log.d("wangjie","当前还有多少个许可${phore.availablePermits()}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            exec.execute(task)
        }
        exec.shutdown()
    }

    private fun testThreadPool() {
        val threadPoolExecutor = ThreadPoolExecutor(2,2,60,TimeUnit.SECONDS,SynchronousQueue(),
            Executors.defaultThreadFactory(), ThreadPoolExecutor.CallerRunsPolicy())
        threadPoolExecutor.allowCoreThreadTimeOut(true) // 核心线程
        threadPoolExecutor.execute { }
    }
}