package com.jack.learn.jetpack.paging

import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("html_url")
    var htmlUrl: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("stargazers_count")
    var stargazersCount: Int
)
