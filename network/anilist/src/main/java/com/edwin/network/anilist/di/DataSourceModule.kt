package com.edwin.network.anilist.di

import com.edwin.network.anilist.MediaNetworkDataSource
import com.edwin.network.anilist.apollo.AniListMediaNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    internal abstract fun bindsMediaNetworkDataSource(
        dataSource: AniListMediaNetwork
    ): MediaNetworkDataSource
}