package com.jack.learn.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.R

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        Person("yang",18)

    }


    fun test() {
        "as".run {
            length
        }
    }
}