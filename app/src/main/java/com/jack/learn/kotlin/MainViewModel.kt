package com.jack.learn.kotlin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainViewModel :ViewModel() {






    // 使用flow构建函数构建出的Flow是属于Cold Flow，也叫做冷流
    val timeFlow = flow {
        var time =0
        while (true) {
            emit(time) // 数据发送器
            delay(1000)
            time++
        }
    }
}