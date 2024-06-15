package com.edwin.network.di

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.BuildConfig
import com.edwin.network.MediaNetworkDataSource
import com.edwin.network.apollo.AniListMediaNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun apolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.ANILIST_GRAPHQL_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideMediaNetworkDataSource(apolloClient: ApolloClient): MediaNetworkDataSource {
        return AniListMediaNetwork(apolloClient)
    }
}