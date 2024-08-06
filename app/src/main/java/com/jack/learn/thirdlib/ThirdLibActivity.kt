package com.jack.learn.thirdlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import com.jack.learn.R
import com.jack.learn.databinding.ActivityThirdLibBinding
import com.jack.learn.thirdlib.gson.UserTypeAdapter
import com.jack.learn.thirdlib.okhttp.TimeEventListener
import com.jack.learn.thirdlib.okhttp.TimeInterceptor
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.IOException

class ThirdLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityThirdLibBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
          testGlide(imageView)
        }
//        testSP()
        testOkHttp()
    }

    private fun testGson() {
        val gson = GsonBuilder().registerTypeAdapter(UserInfo::class.java,UserTypeAdapter()).create()
        val str = gson.toJson("")
        val obj = gson.fromJson("",String::class.java)



    }

    private fun testGlide(imageView: ImageView) {
        val options = RequestOptions()
        Glide.with(this@ThirdLibActivity)
            .load("https://pic.rmb.bdstatic.com/bjh/68e3f5534741633e722a4aee7cd086b16929.jpeg@h_1280")
            .skipMemoryCache(true) // 禁用内存缓存功能
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .placeholder(R.mipmap.image)
            .into(imageView)

        // Glide优化
        // 在lowMemory的时候，调用Glide.cleanMemory()清理掉所有的内存缓存
        // 在其它情况的onTrimMemory()回调中，直接调用Glide.trimMemory()方法来交给Glide处理内存情况
        Glide.get(this).trimMemory(1)
        Glide.get(this).clearMemory()
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
     *  全量更新 会覆盖全部XML文件 通过for循环mModified map集合
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
            .addInterceptor(TimeInterceptor())
//            .authenticator(object : Authenticator { // 授权
//                override fun authenticate(route: Route?, response: Response): Request {
//                    // 刷新token
//                    return response.request.newBuilder().header("Authorization","xxxxx").build()
//                }
//
//            })
            .build()
        val request = Request.Builder().url("https://api.github.com/users/rengwuxian/repos").build()
        val newCall = okHttpClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("wangjie",response.code().toString())
            }

        })
    }

    /**
     * mqtt服务质量等级
     * QoS=0 最多发一次
     * QoS=1 最少发一次
     * QoS=2 保证收一次
     */
    private fun testMqtt() {
        val mqttClient = MqttClient("","")
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {

            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }

        })
    }

}