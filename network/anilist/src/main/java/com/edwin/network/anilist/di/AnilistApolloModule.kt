package com.edwin.network.anilist.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.edwin.network.anilist.BuildConfig
import com.edwin.network.common.apollo.LoggingApolloInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AniListApolloClient

@Module
@InstallIn(SingletonComponent::class)
object AnilistApolloModule {

    @Provides
    @Singleton
    @AniListApolloClient
    internal fun providesAnilistApolloClient(
        ioDispatcher: CoroutineDispatcher,
        loggingApolloInterceptor: ApolloInterceptor
    ): ApolloClient {
        return ApolloClient.Builder()
            .dispatcher(ioDispatcher)
            .serverUrl(BuildConfig.ANILIST_GRAPHQL_URL)
            .addInterceptor(loggingApolloInterceptor)
            .build()
    }
}