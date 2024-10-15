package com.jack.learn.jetpack

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreUtils {

    private val Context.dataStore by preferencesDataStore(name = "StoreName")

    suspend fun saveIntValue(context: Context, keyName: String, value: Int) {
        val intPreferencesKey = intPreferencesKey(keyName)
        context.dataStore.edit {
            it[intPreferencesKey] = value
        }
    }

    suspend fun getIntValue(context: Context, keyName: String, defaultValue: Int? = null) {
        val key = intPreferencesKey(keyName)
        context.dataStore.data.map {
            it[key]?:(defaultValue?:0)
        }.first()
    }

    // 清除数据
    suspend fun clear(context: Context) {
        context.dataStore.edit {
            it.clear()
        }
    }
}