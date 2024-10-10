package com.jack.learn.architecture.mvvm.error

enum class ERROR(var code: Int,var errorMsg: String) {

    /**
     * 无法找到指定位置的资源
     */
    NOT_FOUND(404, "无法找到指定位置的资源"),
    /**
     * 对应HTTP的状态码
     */
    /**
     * 当前请求需要用户验证
     */
    UNAUTHORIZED(401, "当前请求需要用户验证")
}