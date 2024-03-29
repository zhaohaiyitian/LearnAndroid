package com.jack.learn.thirdlib

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jack.learn.R
import com.jack.learn.databinding.ActivityThirdLibBinding
import com.jack.learn.thirdlib.okhttp.TimeEventListener
import com.jack.learn.thirdlib.okhttp.TimeInterceptor
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ThirdLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityThirdLibBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            Glide.with(this@ThirdLibActivity)
                .load("https://img0.baidu.com/it/u=2983778692,229617415&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1711558800&t=2105f81af9b704adc786d71b70fd1dda")
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
        testSP()
    }

    /**
     *  在SharedPreferencesImpl的构造函数中去从文件中加载数据到内存中 并保存在map中 后续的getXXX都是从内存中获取数据，速度比较快
     *  如果getSharedPreferences()比较耗时 sp.getXXX()会一直阻塞
     *
     *  commit 同步提交 可能导致ANR
     *
     *  apply 异步提交 也可能导致ANR
     *  apply Runnable -任务 -加入到QueuedWork队列排队执行
     *  在activity跳转时会在ActivityThread.handlePauseActivity()方法中执行 QueuedWork.waitToFinish();
     *  QueuedWork.waitToFinish():在主线程等待QueuedWork中的任务全部执行完
     *
     *  XML解析
     *  全量更新 会覆盖全部XML文件
     */
    private fun testSP() {
        // name 共享文件的名称，
        // mode 用于指定访问权限 MODE_PRIVATE 只能被本应用程序读和写，其中写入的内容会覆盖源文件的内容
        val edit = getSharedPreferences("jack", MODE_PRIVATE).edit()
        val sp = getSharedPreferences("jack", MODE_PRIVATE)
        sp.getString("","")
        edit.putString("wj","年薪百万")
        edit.putString("wj","年薪百万1111111")
        edit.commit()
        edit.apply()

    }


    /**
     * okhttp
     */
    private fun testOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .eventListener(TimeEventListener())
            .addInterceptor(TimeInterceptor()).build()
        val request = Request.Builder().url("").build()
        val newCall = okHttpClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
            }

        })
    }

}