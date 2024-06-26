package com.edwin.network.kitsu.di

import com.edwin.network.kitsu.KitsuNetworkDataSource
import com.edwin.network.kitsu.apollo.ApolloKitsuNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    internal abstract fun bindsAnilistNetworkDataSource(
        dataSource: ApolloKitsuNetworkDataSource
    ): KitsuNetworkDataSource
}