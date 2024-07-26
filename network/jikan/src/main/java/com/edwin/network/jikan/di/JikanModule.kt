package com.edwin.network.jikan.di

import com.edwin.network.jikan.BuildConfig
import com.edwin.network.jikan.service.JikanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JikanModule {

    @Provides
    @Singleton
    internal fun provideService(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>
    ): JikanService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        // We use callFactory lambda here with dagger.Lazy<Call.Factory>
        // to prevent initializing OkHttp on the main thread.
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()
        .create(JikanService::class.java)
}