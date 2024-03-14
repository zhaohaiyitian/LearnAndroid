package com.jack.learn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.aidl.BinderActivity

class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("learnandroid")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this,BinderActivity::class.java))
        }
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
