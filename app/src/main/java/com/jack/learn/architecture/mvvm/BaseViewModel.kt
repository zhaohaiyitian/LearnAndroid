package com.jack.learn.architecture.mvvm

import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {



    // 需要运行在协程作用域中
    suspend fun <T> safeApiCall(responseBlock: suspend ()->T?,errorBlock: suspend (Int?, String?)->Unit): T?{
        try {
            return responseBlock()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCleared() {
        super.onCleared()
    }
}