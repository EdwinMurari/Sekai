package com.edwin.data.di

import com.edwin.data.repository.MediaRepository
import com.edwin.data.repository.OneOffMediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsMediaRepository(
        repository: OneOffMediaRepository
    ): MediaRepository
}