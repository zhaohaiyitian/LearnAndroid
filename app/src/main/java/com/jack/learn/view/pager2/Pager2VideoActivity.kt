package com.jack.learn.view.pager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.databinding.ActivityPager2VideoBinding

class Pager2VideoActivity : AppCompatActivity() {

    private val mAdapter by lazy { MyFragmentStateAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityPager2VideoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.apply {
            viewPage2.offscreenPageLimit = 1 // 设置几就预加载几个fragment
            viewPage2.adapter = mAdapter
        }
    }
}