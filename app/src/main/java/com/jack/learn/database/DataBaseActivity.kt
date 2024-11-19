package com.jack.learn.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.jack.learn.NBApplication
import com.jack.learn.click
import com.jack.learn.database.sqlite.DataBaseHelper
import com.jack.learn.databinding.ActivityDataBaseBinding
import kotlin.concurrent.thread

class DataBaseActivity : AppCompatActivity() {
    private val database: AppDataBase by lazy { (application as NBApplication).database }
    private val userDao: UserDao by lazy { database.userDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityDataBaseBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.apply {
            save.click {
                userDao.insertAll(User(2,"wangjie"))
            }
        }
        sqlite()
    }

    private fun sqlite() {
        val helper = DataBaseHelper(this,"flower.db",null,1)
        val db = helper.writableDatabase
        db.beginTransaction() // 开启事务
        //TODO 进行数据库操作
        db.setTransactionSuccessful()
        db.endTransaction() // 结束事务
        db.close()
    }
}