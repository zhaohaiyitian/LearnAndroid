package com.jack.learn.thirdlib.okhttp

import android.util.Log
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Protocol
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * EventListener是OkHttp库中的一个接口，用于监听OkHttp请求的各种事件，比如DNS解析、连接建立、请求发送、响应接收等。
 * 通过实现EventListener接口，可以自定义处理这些事件，以便进行日志记录、性能监控、错误处理等操作。
 */
class TimeEventListener: EventListener() {

    private var startTime = 0L
    //在请求开始时调用
    override fun callStart(call: Call) {
        super.callStart(call)
        startTime = System.currentTimeMillis()
    }

    //在请求结束时调用
    override fun callEnd(call: Call) {
        super.callEnd(call)
        val endTime = System.currentTimeMillis()-startTime
        Log.d("wangjie","${endTime}")
    }

    //在连接建立开始时调用
    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
    }

    //在连接建立结束时调用
    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
    }

}