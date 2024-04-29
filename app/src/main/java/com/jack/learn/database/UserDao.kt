package com.jack.learn.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao // 数据操作对象
interface UserDao {

    @Insert
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from users")
    fun getAll():List<User>

    @Update
    fun update(user: User)
}