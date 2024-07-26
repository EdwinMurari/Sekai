package com.edwin.network.userpref.di

import com.edwin.network.userpref.UserPreferencesDataSource
import com.edwin.network.userpref.UserPreferencesDataStoreDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {

    @Binds
    internal abstract fun bindsUserPreferencesDataSource(
        dataSource: UserPreferencesDataStoreDataSource
    ): UserPreferencesDataSource
}