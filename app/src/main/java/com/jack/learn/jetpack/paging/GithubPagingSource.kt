package com.jack.learn.jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class GithubPagingSource:PagingSource<Int,RepositoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
        return try {
            val page = params.key?:1
            val pageSize = params.loadSize
            val rpositories = GithubApiManager.githubServieApi.getRpositories(page, pageSize)
            val items = rpositories.items
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (items.isNotEmpty()) page + 1 else null
            // 三个参数分别是获取到的数据列表，上一页的页数以及下一页的页数
            LoadResult.Page(items,preKey,nextKey)
        } catch (e:Exception) {
            LoadResult.Error(e)
        }
    }
}