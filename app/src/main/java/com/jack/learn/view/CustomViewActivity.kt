package com.jack.learn.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.click
import com.jack.learn.databinding.ActivityCustomViewBinding
import java.util.Random


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
            heartView.click {
                heartView.addHeart(Random().nextInt(6))
            }
        }
    }
}