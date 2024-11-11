package com.jack.learn.jetpack.paging

import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val REPO_LIST = "search/repositories?sort=stars&q=Android"
    }

    @GET(REPO_LIST)
    suspend fun getRpositories(@Query("page") page: Int, @Query("per_page") perPage: Int): RspRepository

}