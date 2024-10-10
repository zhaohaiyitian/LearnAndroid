package com.jack.learn.architecture.mvvm.manager

import com.jack.learn.architecture.mvvm.ApiService

object ApiManager {

    val api by lazy { HttpManager.create(ApiService::class.java) }
}