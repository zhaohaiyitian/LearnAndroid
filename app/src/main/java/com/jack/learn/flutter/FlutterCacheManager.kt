package com.jack.learn.flutter

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.jack.learn.flutter.view.JackImageViewPlugin
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.loader.FlutterLoader
import io.flutter.view.FlutterMain

class FlutterCacheManager private constructor(){


    companion object {
        const val MODULE_NAME_MAIN = "main"
        const val MODULE_NAME_RECOMMEND = "recommend"
        @JvmStatic
        @get:Synchronized
        var instance: FlutterCacheManager?=null
            get() {
                if (field == null) {
                    field = FlutterCacheManager()
                }
                return field
            }
            private set
    }

    // 主线程空闲时 预加载flutter
    private fun preLoad(context: Context) {
        Looper.myQueue().addIdleHandler {
            initFlutterEngine(context,MODULE_NAME_MAIN)
            initFlutterEngine(context,MODULE_NAME_RECOMMEND)
            false
        }
    }


    fun hasCached(moduleName: String): Boolean {
        return FlutterEngineCache.getInstance().contains(moduleName)
    }

    // 获取预加载的flutter
    public fun getCachedFlutterEngine(context: Context?,moduleName: String):FlutterEngine {
        var engine = FlutterEngineCache.getInstance().get(moduleName)
        if (engine == null && context != null) {
            engine = initFlutterEngine(context,moduleName)
        }
        return engine!!
    }


    fun preLoadDartVM(context: Context) {
        val settings = FlutterLoader.Settings()
        FlutterInjector.instance().flutterLoader().startInitialization(context,settings)
        val mainHandler = Handler(Looper.getMainLooper())
        FlutterInjector.instance().flutterLoader().ensureInitializationCompleteAsync(context, arrayOf(),mainHandler) {

        }
    }

    // 初始化Flutter
    private fun initFlutterEngine(context: Context,moduleName: String): FlutterEngine {
        val flutterEngine = FlutterEngine(context)
        // 插件注册要紧跟引擎初始化之后，否则会有在dart中调用插件因为还未初始化完成而导致的时序问题
        JackFlutterBridge.init(flutterEngine)
        JackImageViewPlugin.registerWith(flutterEngine) // 注册Native UI
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint(FlutterLoader().findAppBundlePath(),moduleName))
        FlutterEngineCache.getInstance().put(moduleName,flutterEngine)
        return flutterEngine
    }
}