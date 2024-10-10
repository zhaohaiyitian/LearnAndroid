package com.jack.learn.architecture.mvvm

import com.jack.learn.architecture.mvvm.error.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {


    suspend fun <T> request(requestCall: suspend ()-> BaseResponse<T>?): T?{
        val response = withContext(Dispatchers.IO) {
            requestCall()
        }
        if (response?.isFailed() == true) {
            throw ApiException(response.errorCode,response.errorMsg)
        }
        return response?.data
    }
}