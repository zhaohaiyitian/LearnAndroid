package com.jack.learn.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users") // 定义表名
data class User(
    @PrimaryKey var id: Int = 0, // 定义主键
    @ColumnInfo(name = "first_name") var firstName: String? =null, // 定义列名
    @Ignore var nickname: String?= "" //如果不想让该字段映射成表的列，可以使用该注解标记
)
