package com.jack.learn.jni

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.R
import java.lang.Exception

class JNIActivity : AppCompatActivity() {

    companion object {

        init {
            System.loadLibrary("learn")
        }
    }
    var age = 10.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jni)
        findViewById<TextView>(R.id.tvName).text = stringFromJNI()
        changeAge()
        Log.d("wangjie","$age")
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
    external fun stringFromJNI(): String // C函数
    external fun callAddMethod(number1: Int,number2: Int):Unit
    external fun changeAge()

    // 被c调用的方法
    fun add(number1: Int,number2: Int): Int {
        Log.d("wangjie","被C调用了")
        return number1+number2
    }
//
//    external fun setAntiBiBCallback(callback: IAntiDebugCallback)
//
//    external fun writeTest()
//    external fun readTest()

}
