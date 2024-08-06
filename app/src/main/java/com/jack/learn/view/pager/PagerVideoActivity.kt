package com.jack.learn.view.pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.R
import com.jack.learn.databinding.ActivityPagerVideoBinding

class PagerVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityPagerVideoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}