package com.jack.learn.flutter

import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink

/**
 * 用于数据流（event streams）的通信，持续通信，通常用于Native向Dart的通信，
 * 如：手机电量变化，网络连接变化，陀螺仪，传感器等；
 */
class EventChannelPlugin: EventChannel.StreamHandler {

    private var eventSink: EventSink? = null

    companion object {
        fun registerWith(messenger: BinaryMessenger): EventChannelPlugin {
            val plugin = EventChannelPlugin()
            EventChannel(messenger,"EventChannelPlugin").setStreamHandler(plugin)
            return plugin
        }
    }

    fun send(params: Any) {
        if (eventSink != null) {
            eventSink?.success(params)
        }
    }


    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
    }

    override fun onCancel(arguments: Any?) {
        eventSink = null
    }
}