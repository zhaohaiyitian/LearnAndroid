package com.jack.learn.architecture.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserInfoViewModel:BaseViewModel() {


    private val mRepository by lazy { UserInfoRepository() }

    var userInfoData = MutableLiveData<UserInfo>()


    fun getUser() {
        viewModelScope.launch {
            val userInfo = mRepository.getUserInfo()
            userInfoData.postValue(userInfo)
        }
    }
}