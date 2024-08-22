package com.jack.learn.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.jack.learn.databinding.ActivityCustomViewBinding


class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
//            val animator: ObjectAnimator = ObjectAnimator.ofFloat(customView, "progress", 0f, 275f)
//            animator.duration = 3000
//            animator.interpolator = FastOutSlowInInterpolator()
//            animator.start()

//            llLayout.setOnClickListener {
//                Log.d("wangjie", "ViewGroup")
//            }
//            button.setOnClickListener {
//                // (it.parent as LinearLayout).performClick() 强制让ViewGroup执行click事件
//                Log.d("wangjie", "View")
//            }
        }
    }
}