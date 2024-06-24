package com.jack.learn.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao():UserDao


    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room
                            .databaseBuilder(context.applicationContext,AppDataBase::class.java,"my_database")
                            .enableMultiInstanceInvalidation() // 支持多进程
                            .allowMainThreadQueries() // 允许在主线程操作数据库
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}