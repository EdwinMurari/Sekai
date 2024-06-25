package com.edwin.network.anilist.di

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.anilist.BuildConfig
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