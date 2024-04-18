package com.jack.learn.flutter

import android.app.Activity
import android.widget.Toast
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/**
 *  MethodChannelPlugin
 *  用于传递方法调用（method invocation），一次性通信，通常用于Dart调用Native的方法：如拍照；
 */
class MethodChannelPlugin(private var activity: Activity):MethodCallHandler {

    companion object {
        fun registerWith(messenger:BinaryMessenger,activity: Activity) {
            val channel = MethodChannel(messenger,"MethodChannelPlugin")
            val instance = MethodChannelPlugin(activity)
            channel.setMethodCallHandler(instance)
        }
    }



    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            "send" -> { // 处理来自Dart的方法调用
                showMessage(call.arguments())
                result.success("MethodChannelPlugin收到：" + call.arguments) // 返回结果给Dart
            }
        }

    }

    private fun showMessage(args: String?) {
        if (activity is IShowMessage) {
            (activity as IShowMessage).onShowMessage(args)
        }
        Toast.makeText(activity, args, Toast.LENGTH_SHORT).show()
    }
}