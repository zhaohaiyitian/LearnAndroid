package com.jack.learn

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.kwai.koom.base.DefaultInitTask
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import leakcanary.LeakCanary

/**
 *
 */

class NBApplication: Application() {

    companion object {
        const val CACHED_ENGINE_ID = "MY_CACHED_ENGINE_ID"
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
//        val rootDir = MMKV.initialize(this)
//        Log.d("wangjie",rootDir)
//        val kv = MMKV.defaultMMKV()
//        kv.encode("bool",true)
//        val bool = kv.decodeBool("bool")
//        Log.d("wangjie","$bool")

        // JVMTI
//        Monitor.init(this)
//        Monitor.writeFilters("jack")
        initKoom()
        //在NBApplication中预先初始化Flutter引擎以提升Flutter页面打开速度
        // 一个Engine代表着一个flutter进程实例(dart解析器，flutter平台线程)
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put(CACHED_ENGINE_ID,flutterEngine)
    }

    private fun initKoom() {
        DefaultInitTask.init(this)
    }

    private fun lifecycle() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
        unregisterActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}