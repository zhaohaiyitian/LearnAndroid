package com.jack.learn.thirdlib

import com.google.gson.annotations.SerializedName

data class UserInfo(
    var id:String? = null,


    var name:String? = null,

    @SerializedName("_age", alternate =["names"])
    var age:Int? = null
)
