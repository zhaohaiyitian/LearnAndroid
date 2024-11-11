package com.jack.learn.jetpack.paging

import com.google.gson.annotations.SerializedName

class RspRepository {

    @SerializedName("total_count")
    var totalCount: Int = 0
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    @SerializedName("items")
    var items: List<RepositoryItem> = emptyList()
}