package com.jack.learn.flutter.view

import android.content.Context
import android.view.View
import io.flutter.plugin.platform.PlatformView

class JackImageViewController(context: Context,args: Any?):PlatformView {

    private var imageView: JackImageView = JackImageView(context)


    init {
        if (args is Map<*,*>) {
            imageView.setUrl(args["url"] as String)
        }
    }
    override fun getView(): View {
        return imageView
    }

    override fun dispose() {
        // 资源释放
    }
}