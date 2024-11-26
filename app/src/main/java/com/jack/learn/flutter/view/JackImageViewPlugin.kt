package com.jack.learn.flutter.view

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry
import io.flutter.plugin.common.PluginRegistry

object JackImageViewPlugin {

    fun registerWith(register: PluginRegistry.Registrar) {
        val viewFactory = JackImageViewFactory()
        register.platformViewRegistry().registerViewFactory("JackImageView",viewFactory)
    }

    fun registerWith(flutterEngine: FlutterEngine) {
        val shimPluginRegistry = ShimPluginRegistry(flutterEngine)
        registerWith(shimPluginRegistry.registrarFor("JackFlutter"))
    }
}