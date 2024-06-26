package com.edwin.network.jikan.di

import com.edwin.network.jikan.JikanNetworkDataSource
import com.edwin.network.jikan.retrofit.RetrofitJikanNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JikanDataSourceModule {

    @Binds
    internal abstract fun bindsMediaNetworkDataSource(
        dataSource: RetrofitJikanNetworkDataSource
    ): JikanNetworkDataSource
}