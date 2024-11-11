package com.jack.learn.jetpack.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

object Repository {
    private const val PAGE_SIZE = 25

    fun getGithubPagingData(): Flow<PagingData<RepositoryItem>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {GithubPagingSource()}
        ).flow
    }
}