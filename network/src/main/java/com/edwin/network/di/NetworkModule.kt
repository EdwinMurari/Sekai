package com.edwin.network.di

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Provides
    @Singleton
    internal fun apolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.ANILIST_GRAPHQL_URL)
            .build()
    }

    @Provides
    @Singleton
    internal fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}