package com.edwin.network.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonModule {

    @Provides
    @Singleton
    internal fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}