package com.jack.learn.architecture.mvvm

import com.jack.learn.architecture.mvvm.manager.ApiManager


class UserInfoRepository: BaseRepository() {


    suspend fun getUserInfo():UserInfo? {

        return request {
            ApiManager.api.fetchUserData()
        }
    }
}