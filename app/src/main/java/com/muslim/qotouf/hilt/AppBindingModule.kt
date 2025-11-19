package com.muslim.qotouf.hilt

import com.muslim.qotouf.data.local.data_store.DataStoreImpl
import com.muslim.qotouf.data.local.data_store.IDataStore
import com.muslim.qotouf.data.repo.IRepo
import com.muslim.qotouf.data.repo.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {
    @Binds
    @Singleton
    abstract fun bindIDataStore(
        dataStoreImpl: DataStoreImpl
    ): IDataStore

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImpl: RepoImpl
    ): IRepo
}