package com.jack.learn.flutter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.flutter.embedding.android.FlutterTextureView
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

abstract class MyFlutterFragment:Fragment() {

    private var flutterEngine:FlutterEngine? = null
    private var flutterView:FlutterView? = null
    var cached = FlutterCacheManager.instance!!.hasCached("")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        flutterEngine = FlutterEngine(context)
        flutterEngine?.dartExecutor?.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 注册flutter/platform_views插件以便能够处理native view
        if (!cached) {
            flutterEngine!!.platformViewsController.attach(activity,flutterEngine!!.renderer,flutterEngine!!.dartExecutor)
        }


        // 执行addView添加flutterView
    }

    private fun createFlutterView(context: Context):FlutterView {
        // 使用FlutterTextureView进行渲染，以规避FlutterSurfaceView压后台回来后界面被复用的问题
        val flutterTextureView = FlutterTextureView(context)
        flutterView = FlutterView(context,flutterTextureView)
        return flutterView!!
    }


    override fun onStart() {
        flutterView?.attachToFlutterEngine(flutterEngine!!)
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
        // flutter >=1.17
        flutterEngine?.lifecycleChannel?.appIsResumed()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine?.lifecycleChannel?.appIsInactive()
    }

    override fun onStop() {
        super.onStop()
        flutterEngine?.lifecycleChannel?.appIsPaused()
    }

    override fun onDestroy() {
        super.onDestroy()
        flutterView?.detachFromFlutterEngine()
    }

    override fun onDetach() {
        super.onDetach()
        flutterEngine?.lifecycleChannel?.appIsDetached()
    }
}