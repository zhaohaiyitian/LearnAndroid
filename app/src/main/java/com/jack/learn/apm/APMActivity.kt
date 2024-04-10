package com.jack.learn.apm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.jack.learn.R

class APMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_apmactivity)
        AsyncLayoutInflater(this).inflate(R.layout.activity_apmactivity,null,object: AsyncLayoutInflater.OnInflateFinishedListener {
            override fun onInflateFinished(view: View, resid: Int, parent: ViewGroup?) {
                setContentView(view)
            }
        })
        val byte = ByteArray(360000*1024)
    }
}