package com.jack.learn.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MainViewModel:ViewModel() {
    fun getPagingData(): Flow<PagingData<RepositoryItem>> {

        return Repository.getGithubPagingData().cachedIn(viewModelScope)
    }
}