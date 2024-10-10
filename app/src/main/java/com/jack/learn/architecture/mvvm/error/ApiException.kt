package com.jack.learn.architecture.mvvm.error

open class ApiException: Exception {

    var errCode: Int
    var errMsg: String

    constructor(error: ERROR,e:Throwable? =null):super(e) {
        errCode = error.code
        errMsg = error.errorMsg
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.errCode = code
        this.errMsg = msg
    }
}