package com.jack.learn.architecture.mvvm

data class BaseResponse<out T>(
    val data: T?,
    val errorCode: Int = 0, // 服务器状态码 这里0表示请求成功
    val errorMsg: String = "" // 错误信息
)
