package com.jack.learn.jetpack

import android.os.CountDownTimer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class UserViewModel:ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData()

    val liveData1 = MutableLiveData<String>()
    val liveData2 = MutableLiveData<String>()
    val mergerLiveData: MediatorLiveData<String> = MediatorLiveData()
    fun setText(count: Long) {
        for (i in 0..10) {
            // 循环调用postValue只会收到最后一次发送的数据
            text.postValue("时间： "+i)
        }
    }

    // 合并两个LiveData
    fun updateContent(): MediatorLiveData<String> {
        mergerLiveData.addSource(liveData1, object : Observer<String> {
            override fun onChanged(t: String?) {
                mergerLiveData.value = t
            }

        })
        mergerLiveData.addSource(liveData2, object : Observer<String> {
            override fun onChanged(t: String?) {
                mergerLiveData.value = t
            }

        })
        return mergerLiveData
    }

    fun mergerTest() {
        object: CountDownTimer(1*60*1000,3*1000) {
            override fun onTick(millisUntilFinished: Long) {
                liveData1.postValue("网络数据有更新+${millisUntilFinished/1000}")
            }

            override fun onFinish() {
            }

        }.start()
        object: CountDownTimer(1*60*1000,5*1000) {
            override fun onTick(millisUntilFinished: Long) {
                liveData1.postValue("本地数据有更新+${millisUntilFinished/1000}")
            }

            override fun onFinish() {
            }

        }.start()
    }
}