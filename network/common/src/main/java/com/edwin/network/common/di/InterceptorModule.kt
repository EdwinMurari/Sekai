package com.edwin.network.common.di

import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.edwin.network.common.apollo.LoggingApolloInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    internal fun providesLoggingInterceptor(): ApolloInterceptor {
        return LoggingApolloInterceptor()
    }
}