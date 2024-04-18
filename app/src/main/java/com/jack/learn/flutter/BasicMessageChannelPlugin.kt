package com.jack.learn.flutter

import android.app.Activity
import android.widget.Toast
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StringCodec

/**
 * 用于传递字符串和半结构化的信息，持续通信，如：Native将遍历到的文件信息陆续传递到Dart
 * 再比如：Flutter将从服务端陆陆续获取到的信息交给Native加工，Native处理完返回等
 */
class BasicMessageChannelPlugin:BasicMessageChannel.MessageHandler<String>,BasicMessageChannel.Reply<String> {

    private var activity: Activity? = null
    private var messageChannel: BasicMessageChannel<String>? = null
    constructor(messenger: BinaryMessenger,activity: Activity) {
        this.activity = activity
        messageChannel = BasicMessageChannel(messenger,"BasicMessageChannelPlugin",StringCodec.INSTANCE)
        //设置消息处理器，处理来自Dart的消息
        messageChannel?.setMessageHandler(this)
    }

    companion object {
        fun registerWith(messenger: BinaryMessenger,activity: Activity): BasicMessageChannelPlugin {
            return BasicMessageChannelPlugin(messenger, activity)
        }
    }

    fun send(message: String?,callback: BasicMessageChannel.Reply<String>) {
        messageChannel?.send(message,callback)
    }

    override fun onMessage(message: String?, reply: BasicMessageChannel.Reply<String>) {//处理Dart发来的消息
        reply.reply("BasicMessageChannel收到：" + message) // 可以通过reply进行回复
        if (activity is IShowMessage) {
            (activity as IShowMessage).onShowMessage(message)
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun reply(reply: String?) {
    }
}