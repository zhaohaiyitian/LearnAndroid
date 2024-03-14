package com.jack.learn.jni

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.R

class JNIActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("learnandroid")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        Log.d("wangjie",stringFromJNI())

        setAntiBiBCallback(object : IAntiDebugCallback {
            override fun beInjectedDebug() {
                Log.d("wangjie","beInjectedDebug")
            }

        })
    }

    //静态注入
    external fun stringFromJNI(): String // C函数

    external fun setAntiBiBCallback(callback: IAntiDebugCallback)

}
