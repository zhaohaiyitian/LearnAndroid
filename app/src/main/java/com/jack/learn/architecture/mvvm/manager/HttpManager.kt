package com.jack.learn.architecture.mvvm.manager

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {
    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .client(initOkHttpClient())
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 获取 apiService
     */
    fun <T> create(apiService: Class<T>): T {
        return mRetrofit.create(apiService)
    }

    /**
     * 初始化OkHttp
     */
    private fun initOkHttpClient(): OkHttpClient {
        val build = OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
        // 添加参数拦截器
        val interceptors = mutableListOf<Interceptor>()
//        build.addInterceptor(CookiesInterceptor())
//        build.addInterceptor(HeaderInterceptor())

        //日志拦截器
//        val logInterceptor = HttpLoggingInterceptor { message: String ->
//            Log.i("okhttp", "data:$message")
//        }
//        if (SumAppHelper.isDebug()) {
//            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
//        }
//        build.addInterceptor(logInterceptor)
        //网络状态拦截
//        build.addInterceptor(object : Interceptor {
//            override fun intercept(chain: Interceptor.Chain): Response {
//                if (NetworkUtil.isConnected(SumAppHelper.getApplication())) {
//                    val request = chain.request()
//                    return chain.proceed(request)
//                } else {
//                    throw NoNetWorkException(ERROR.NETWORD_ERROR)
//                }
//            }
//        })
        return build.build()
    }
}