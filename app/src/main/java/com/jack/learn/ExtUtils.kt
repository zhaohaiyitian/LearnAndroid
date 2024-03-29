package com.jack.learn

import android.view.View

fun View.click(block: ()->Unit) {
    setOnClickListener {
        block()
    }
}