package com.edwin.network.kitsu.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.edwin.network.kitsu.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KitsuApolloClient

@Module
@InstallIn(SingletonComponent::class)
object KitsuApolloModule {

    @Provides
    @Singleton
    @KitsuApolloClient
    internal fun providesKitsuApolloClient(
        ioDispatcher: CoroutineDispatcher,
        loggingApolloInterceptor: ApolloInterceptor
    ): ApolloClient {
        return ApolloClient.Builder()
            .dispatcher(ioDispatcher)
            .serverUrl(BuildConfig.GRAPHQL_URL)
            .addInterceptor(loggingApolloInterceptor)
            .build()
    }
}