package com.jack.learn.jni

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.R
import java.lang.Exception

class JNIActivity : AppCompatActivity() {

//    companion object {
//        init {
//            System.loadLibrary("learnandroid")
//        }
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
//        findViewById<TextView>(R.id.tvName).text = stringFromJNI()
//
//        setAntiBiBCallback(object : IAntiDebugCallback {
//            override fun beInjectedDebug() {
//                Log.d("wangjie","beInjectedDebug")
//            }
//
//        })

//        Thread {
//            try {
//                writeTest()
//                Thread.sleep(2000)
//                readTest()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }.start()
    }

    //静态注入
//    external fun stringFromJNI(): String // C函数
//
//    external fun setAntiBiBCallback(callback: IAntiDebugCallback)
//
//    external fun writeTest()
//    external fun readTest()

}
