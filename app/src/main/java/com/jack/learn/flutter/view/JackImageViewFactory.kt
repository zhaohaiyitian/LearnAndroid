package com.jack.learn.flutter.view

import android.content.Context
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class JackImageViewFactory:PlatformViewFactory(StandardMessageCodec.INSTANCE) { // 传入默认的编解码器
    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        return JackImageViewController(context,args)
    }
}