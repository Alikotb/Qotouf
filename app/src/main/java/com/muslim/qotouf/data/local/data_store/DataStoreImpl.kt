package com.muslim.qotouf.data.local.data_store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.muslim.qotouf.utils.constant.AppConstant
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore by preferencesDataStore(name = AppConstant.DATA_STORE_NAME)

@Suppress("UNCHECKED_CAST")
@Singleton
class DataStoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) :IDataStore{
    private val dataStore = context.dataStore
    override suspend fun <T> saveData(key: String, value: T) {
        dataStore.edit {
            when (value) {
                is String -> {
                    it[stringPreferencesKey(key)] = value
                }

                is Int -> {
                    it[intPreferencesKey(key)] = value
                }

                is Boolean -> {
                    it[booleanPreferencesKey(key)] = value
                }

                is Float -> {
                    it[floatPreferencesKey(key)] = value
                }

                is Long -> {
                    it[longPreferencesKey(key)] = value
                }

                else -> throw IllegalArgumentException("Unsupported type")
            }
        }

    }

    override fun <T> getData(key: String, defaultValue: T): Flow<T> {
        return when (defaultValue) {
            is String -> {
                dataStore.data.map {
                    (it[stringPreferencesKey(key)] ?: defaultValue) as T
                }
            }

            is Int -> {
                dataStore.data.map {
                    (it[intPreferencesKey(key)] ?: defaultValue) as T
                }
            }

            is Boolean -> {
                dataStore.data.map {
                    (it[booleanPreferencesKey(key)] ?: defaultValue) as T
                }
            }

            is Float -> {
                dataStore.data.map {
                    (it[floatPreferencesKey(key)] ?: defaultValue) as T
                }
            }

            is Long -> {
                dataStore.data.map {
                    (it[longPreferencesKey(key)] ?: defaultValue) as T
                }
            }

            else -> throw IllegalArgumentException("Unsupported type")

        }

    }
    fun getBooleanSync(key: String, default: Boolean = false): Boolean = runBlocking {
        try {
            val preferencesKey = booleanPreferencesKey(key)
            dataStore.data.first()[preferencesKey] ?: default
        } catch (e: Exception) {
            default
        }
    }


}