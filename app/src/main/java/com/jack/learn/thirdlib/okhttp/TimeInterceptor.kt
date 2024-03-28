package com.jack.learn.thirdlib.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TimeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.d("wangjie","${(t2-t1)}")
        return response
    }
}