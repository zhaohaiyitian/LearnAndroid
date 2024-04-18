package com.jack.learn.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jack.learn.R
import io.flutter.embedding.android.FlutterActivity

class FlutterAppActivity : FlutterActivity(), IShowMessage {

    private var initParams: String? = ""

    private var basicMessageChannelPlugin: BasicMessageChannelPlugin? = null
    private var eventChannelPlugin: EventChannelPlugin? = null
    companion object {
        const val INIT_PARAMS = "initParams"
        fun start(context: Context,params: String) {
            val intent = Intent(context,FlutterAppActivity::class.java)
            intent.putExtra(INIT_PARAMS,params)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams = intent.getStringExtra(INIT_PARAMS)!!

        if (flutterEngine != null) {
            eventChannelPlugin = EventChannelPlugin.registerWith(flutterEngine!!.dartExecutor)
            MethodChannelPlugin.registerWith(flutterEngine!!.dartExecutor, this)
            basicMessageChannelPlugin = BasicMessageChannelPlugin.registerWith(flutterEngine!!.dartExecutor, this)
        }
    }

    /**
     * 重载该方法来传递初始化参数
     */
    override fun getInitialRoute(): String? {
        return if (initParams == null) super.getInitialRoute() else initParams
    }

    override fun onShowMessage(args: String?) {

    }

    override fun sendMessage(message: String?, useEventChannel: Boolean) {
        if (useEventChannel) {
            eventChannelPlugin!!.send(message!!)
        } else {
            basicMessageChannelPlugin!!.send(message) { message: String? ->
                onShowMessage(message)
            }
        }
    }
}