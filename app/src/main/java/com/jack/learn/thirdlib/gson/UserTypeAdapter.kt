package com.jack.learn.thirdlib.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.jack.learn.thirdlib.UserInfo

class UserTypeAdapter:TypeAdapter<UserInfo>() {
    override fun write(out: JsonWriter, value: UserInfo) {
        out.beginObject()
        out.name("name").value(value.name)
        out.endObject()
    }

    override fun read(in_: JsonReader): UserInfo {
        val userInfo = UserInfo() // 可以用对象池优化
        in_.beginObject()
        while (in_.hasNext()) {
            when(in_.nextName()) {
                "" -> {
                    userInfo.name = in_.nextString()
                }
                "" -> {

                }
            }
        }
        in_.endObject()
        return userInfo
    }
}