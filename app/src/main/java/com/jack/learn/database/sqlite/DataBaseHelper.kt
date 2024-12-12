package com.jack.learn.database.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * context 上下文
 * name 数据库名称
 * factory 游标工厂
 * version 版本号
 */
class DataBaseHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    private val DATABASE_NAME = "my_database.db"
    private val DATABASE_VERSION = 1
    private val TABLE_NAME = "users"
    private val COLUMN_ID = "id"
    private val COLUMN_NAME = "name"
    private val COLUMN_AGE = "age"

    // 第一次创建数据库的时候被调用
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER)"
        db.execSQL(createTableQuery)
    }

    // 升级数据库的回调
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    private fun insertUser(db: SQLiteDatabase,name: String,age: Long) {
        val insertQuery = "INSERT INTO $TABLE_NAME($COLUMN_NAME, $COLUMN_AGE) VALUES (?,?)"
        val stmt = db.compileStatement(insertQuery)
        stmt.bindString(1,name)
        stmt.bindLong(2,age)
        // 执行预编译语句
        stmt.executeInsert()
    }
}