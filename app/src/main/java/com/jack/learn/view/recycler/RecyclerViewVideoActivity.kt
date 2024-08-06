package com.jack.learn.view.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import com.jack.learn.R
import com.jack.learn.databinding.ActivityRecyclerViewVideoBinding

class RecyclerViewVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewbinding = ActivityRecyclerViewVideoBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        viewbinding.apply {
            PagerSnapHelper().attachToRecyclerView(recyclerview)
        }
    }
}