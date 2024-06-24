package com.jack.learn.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.jack.learn.NBApplication
import com.jack.learn.click
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
    }
}