package com.jack.learn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.aidl.BinderActivity
import com.jack.learn.jni.IAntiDebugCallback
import com.jack.learn.jni.JNIActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this,JNIActivity::class.java))
        }
    }


}
