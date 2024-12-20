package com.jack.learn

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.core.os.TraceCompat
import androidx.tracing.Trace
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.jack.learn.architecture.plugin.HookUtil
import com.jack.learn.architecture.plugin.LoadUtil
import com.jack.learn.database.AppDataBase
import com.jack.skin_core.SkinManager
import com.kwai.koom.base.DefaultInitTask
import com.tencent.mmkv.MMKV
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


/**
 *
 */

class NBApplication: Application() {

    private var handler:Handler? = null
//    private var mResources:Resources? = null
    val database: AppDataBase by lazy { AppDataBase.getInstance(applicationContext) }
    companion object {
        const val CACHED_ENGINE_ID = "MY_CACHED_ENGINE_ID"
        private const val MEMORY_MONITOR_INTERVAL = 1000 * 60L

        var isHttpAlive = true
    }
    override fun attachBaseContext(base: Context?) {
        Trace.beginSection("")
        super.attachBaseContext(base)
        Trace.endSection()
    }

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog()
//            ARouter.openDebug()
//        }
//        ARouter.init(this) // 初始化
        // 方法耗时统计
//        Debug.startMethodTracingSampling("",20000,100)
//        Debug.startMethodTracing("wangjie")

        //初始化换肤框架
//        SkinManager.getInstance().init(this)
//        SkinManager.getInstance().loadSkinApk(getExternalFilesDir("").toString()+"/skin.apk")

         // 初始化插件化
//        LoadUtil.loadClass(this)
////        mResources = HookUtil.loadResource(this,getExternalFilesDir("").toString()+"/pluggable-debug.apk")
//        HookUtil.hookAMS()
//        HookUtil.hookHandler()


        MMKV.initialize(this)
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


        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault()) // 执行dart代码
        FlutterEngineCache.getInstance().put(CACHED_ENGINE_ID,flutterEngine)
        initClearMemory()
//        Debug.stopMethodTracing()
    }

//    override fun getResources(): Resources {
//        if (mResources == null) {
//            return super.getResources()
//        }
//        return mResources!!
//    }

    /**
     * 开启低内存检测，如果低内存了 作出相应的操作
     */
    private fun initClearMemory() {
        val handlerThread = HandlerThread("thread_monitor_low_memory")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        handler?.postDelayed(releaseMemoryCacheRunner,MEMORY_MONITOR_INTERVAL)
    }

    /**
     * 如果已用内存达到了总的80%时 就清空缓存
     */
    private val releaseMemoryCacheRunner = object: Runnable {

        override fun run() {
            val alreadyUsedMemorySize = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()
            val maxMemory = Runtime.getRuntime().maxMemory()
            if (alreadyUsedMemorySize>maxMemory*0.8) {
                Glide.get(this@NBApplication).clearMemory()
                Glide.get(this@NBApplication).clearDiskCache()
            }
//            handler?.postDelayed(releaseMemoryCacheRunner,MEMORY_MONITOR_INTERVAL)
        }
    }

    private fun initKoom() {
        DefaultInitTask.init(this)
    }

    private fun lifecycle() {
        // 注册Activity的生命周期回调
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

    override fun onTerminate() {
        super.onTerminate()
//        ARouter.getInstance().destroy()//是释放资源的API。释放代码写在Application生命周期的onTerminate( )里。
    }
}