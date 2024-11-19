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


    // 第一次创建数据库的时候被调用
    override fun onCreate(db: SQLiteDatabase?) {

    }

    // 升级数据库的回调
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}