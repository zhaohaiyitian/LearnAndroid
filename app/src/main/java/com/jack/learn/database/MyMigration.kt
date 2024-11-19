package com.jack.learn.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MyMigration(startVersion: Int, endVersion: Int) : Migration(startVersion, endVersion) {
    override fun migrate(database: SupportSQLiteDatabase) {

    }
}