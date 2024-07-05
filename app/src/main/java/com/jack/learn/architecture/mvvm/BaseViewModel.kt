package com.jack.learn.architecture.mvvm

import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {



    suspend fun <T> safeApiCall(responseBlock: suspend ()->T?): T?{

        return responseBlock()
    }

    override fun onCleared() {
        super.onCleared()
    }
}