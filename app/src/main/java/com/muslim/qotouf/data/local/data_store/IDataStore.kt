package com.muslim.qotouf.data.local.data_store

import kotlinx.coroutines.flow.Flow

interface IDataStore {
    suspend fun <T> saveData(key: String, value: T)
    fun <T> getData(key: String, defaultValue: T): Flow<T>
}