package com.jack.learn.thirdlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jack.learn.R
import com.jack.learn.databinding.ActivityThirdLibBinding

class ThirdLibActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityThirdLibBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            Glide.with(this@ThirdLibActivity)
                .load("https://img0.baidu.com/it/u=2983778692,229617415&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1711558800&t=2105f81af9b704adc786d71b70fd1dda")
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}