package com.jack.learn.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao():UserDao
}