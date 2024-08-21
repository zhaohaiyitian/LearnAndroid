package com.jack.learn.hotfix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jack.learn.R
import com.jack.learn.databinding.ActivityHotFixBinding

class HotFixActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityHotFixBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {

        }
    }
}