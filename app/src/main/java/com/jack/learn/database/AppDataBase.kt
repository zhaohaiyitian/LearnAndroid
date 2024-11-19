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
                            .fallbackToDestructiveMigration() // 破坏性的数据库迁移
                            .fallbackToDestructiveMigrationFrom(2) //部分破坏性的数据库迁移
                            .addMigrations(MyMigration(1,2)) // 初始版本-目标版本
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}