package com.jack.learn.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.jack.learn.databinding.ActivityDataBaseBinding
import kotlin.concurrent.thread

class DataBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityDataBaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        thread {
            // 创建内存数据库
            Room.inMemoryDatabaseBuilder(applicationContext,AppDataBase::class.java)
            // 创建本地持久化的数据库
            val db = Room.databaseBuilder(applicationContext,AppDataBase::class.java,"database-name").build()
            val userDao = db.userDao()
            userDao.insertAll(User(2,"wangjie"))
        }
    }
}