package com.jack.learn.architecture.mvvm

interface ApiService {





    suspend fun fetchUserData():BaseResponse<UserInfo>
}