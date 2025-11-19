package com.muslim.qotouf.data.repo

import kotlinx.coroutines.flow.Flow

interface IRepo {
    suspend fun <T> saveData(key: String, value: T)
    fun <T> getData(key: String, defaultValue: T): Flow<T>
}