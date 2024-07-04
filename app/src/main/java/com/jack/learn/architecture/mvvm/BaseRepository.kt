package com.jack.learn.architecture.mvvm

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {


    suspend fun <T> request(requestCall: suspend ()-> BaseResponse<T>?): T?{
        val response = withContext(Dispatchers.IO) {
            requestCall()
        }
        return response?.data
    }
}