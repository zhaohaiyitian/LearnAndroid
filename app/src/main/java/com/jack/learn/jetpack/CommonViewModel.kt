package com.jack.learn.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel:ViewModel() {

    val commonData: MutableLiveData<String> = MutableLiveData()

    fun updateContent(name: String) {
        commonData.postValue(name)
    }


    override fun onCleared() {
        super.onCleared()
        // 进行协程的取消
    }
}