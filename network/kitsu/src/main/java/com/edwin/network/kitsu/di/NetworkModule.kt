package com.edwin.network.kitsu.di

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.kitsu.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun apolloClient(ioDispatcher: CoroutineDispatcher): ApolloClient {
        return ApolloClient.Builder()
            .dispatcher(ioDispatcher)
            .serverUrl(BuildConfig.GRAPHQL_URL)
            .build()
    }

    @Provides
    @Singleton
    internal fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}