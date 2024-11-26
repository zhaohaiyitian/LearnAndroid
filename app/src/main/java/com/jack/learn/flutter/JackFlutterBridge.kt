package com.jack.learn.flutter

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * Flutter和Native通信插件
 */
class JackFlutterBridge:IJackBridge<Any?,MethodChannel.Result>,MethodChannel.MethodCallHandler {

    var methodChannels = mutableListOf<MethodChannel>()
    companion object {
        @JvmStatic
        var instance: JackFlutterBridge? = null
            private set
        @JvmStatic
        fun init(flutterEngine: FlutterEngine):JackFlutterBridge {
            val methodChannel = MethodChannel(flutterEngine.dartExecutor,"JackFlutterBridge")
            if (instance == null) {
                JackFlutterBridge().also { instance = it }
            }
            methodChannel.setMethodCallHandler(instance)
            // 因多FlutterEngine后每个FlutterEngine需要单独注册一个MethodChannel，所以用集合将每个MethodChannel保存起来
            instance!!.apply {
                methodChannels.add(methodChannel)
            }
            return instance!!
        }
    }

     // Native to Flutter
    fun fire(method: String,arguments: Any?) {
        methodChannels.forEach {
            it.invokeMethod(method,arguments)
        }

    }

    // Native to Flutter
    fun fire(method: String,arguments: Any?,callBack: MethodChannel.Result) {
        methodChannels.forEach {
            it.invokeMethod(method,arguments,callBack)
        }

    }

    override fun onBack(p: Any?) {

    }

    override fun goToNative(p: Any?) {
        if (p is Map<*,*>) {
            val action = p["action"]
            if (action == "goToDetail") {
                // 处理跳转逻辑
            }
        }
    }

    override fun getHeadParams(callBack: MethodChannel.Result) {
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            "onBack" -> onBack(call.arguments)
            "getHeadParams" -> getHeadParams(result)
            "goToNative" -> goToNative(call.arguments)
            else -> result.notImplemented()

        }
    }
}