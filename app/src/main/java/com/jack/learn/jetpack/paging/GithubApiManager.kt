package com.jack.learn.jetpack.paging

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiManager {

    val githubServieApi: GithubService by lazy {
        val retrofit = retrofit2.Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GithubService::class.java)
    }


}