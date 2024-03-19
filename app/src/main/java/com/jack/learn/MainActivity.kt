package com.jack.learn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.aidl.BinderActivity
import com.jack.learn.jetpack.MVVMActivity
import com.jack.learn.jni.IAntiDebugCallback
import com.jack.learn.jni.JNIActivity
import com.jack.learn.kotlin.KotlinActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.jni).setOnClickListener {
            startActivity(Intent(this,JNIActivity::class.java))
        }

        findViewById<Button>(R.id.jetpack).setOnClickListener {
            startActivity(Intent(this,MVVMActivity::class.java))
        }
        findViewById<Button>(R.id.btnKotlin).setOnClickListener {
            startActivity(Intent(this,KotlinActivity::class.java))
        }
    }


}
