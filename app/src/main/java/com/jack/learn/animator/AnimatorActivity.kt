package com.jack.learn.animator

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jack.learn.R
import com.jack.learn.click
import com.jack.learn.databinding.ActivityAnimatorBinding
import java.util.concurrent.CountDownLatch
import java.util.concurrent.locks.ReentrantLock

class AnimatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityAnimatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            button.post {  }
            button.click {
                val animator = ObjectAnimator.ofFloat(button, "rotation", 0f, 360f)
                animator.duration = 2000
                animator.start()
//                val animator1 = ObjectAnimator.ofFloat(button, "scaleY", 1f, 2f)
//                animator1.duration = 2000
//                animator1.start()
            }
        }
    }
}