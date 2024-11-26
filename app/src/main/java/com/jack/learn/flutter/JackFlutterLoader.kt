package com.jack.learn.flutter

import android.content.Context
import io.flutter.embedding.engine.loader.FlutterLoader

/**
 * Flutter热修复
 */
class JackFlutterLoader:FlutterLoader() {

    companion object {
        const val FIX_SO = "libappfix.so"
        private var instance:JackFlutterLoader? = null
            get() {
                if (field == null) {
                    field = JackFlutterLoader()
                }
                return field
            }
        fun get():JackFlutterLoader {
            return instance!!
        }
    }

    override fun ensureInitializationComplete(
        applicationContext: Context,
        args: Array<out String>?
    ) {
        // 通过反射更改so的路径
        val field = FlutterLoader::class.java.getDeclaredField("")
        field.isAccessible = true
        super.ensureInitializationComplete(applicationContext, args)
    }
}