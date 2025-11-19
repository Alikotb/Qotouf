package com.muslim.qotouf.data.repo

import com.muslim.qotouf.data.local.data_store.IDataStore
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val dataStore: IDataStore
): IRepo {
    override suspend fun <T> saveData(key: String, value: T) {
        dataStore.saveData(key,value)
    }

    override fun <T> getData(key: String, defaultValue: T) = dataStore.getData(key,defaultValue)
}