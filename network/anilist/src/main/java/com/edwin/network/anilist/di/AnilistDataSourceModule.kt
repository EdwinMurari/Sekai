package com.edwin.network.anilist.di

import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.anilist.apollo.ApolloAnilistNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnilistDataSourceModule {

    @Binds
    internal abstract fun bindsAnilistNetworkDataSource(
        dataSource: ApolloAnilistNetworkDataSource
    ): AnilistNetworkDataSource
}