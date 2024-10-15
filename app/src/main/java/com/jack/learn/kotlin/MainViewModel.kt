package com.jack.learn.kotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

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

    val stateFlow1 = timeFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)


    //StateFlow 粘性
    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    fun startTimer() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                _stateFlow.value +=1
            }
        },0,1000)
    }


    //SharedFlow 非粘性
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun startLogin() {
        viewModelScope.launch {
            _sharedFlow.emit("Login Success")
        }
    }

}