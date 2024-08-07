package com.edwin.data.di

import com.edwin.data.repository.PagedMediaRepository
import com.edwin.data.repository.impl.AnilistPagedMediaRepository
import com.edwin.data.repository.impl.ExtensionPagedMediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnilistPagedMediaRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExtensionPagedMediaRepo

@Module
@InstallIn(SingletonComponent::class)
abstract class PagedRepoModule {

    @AnilistPagedMediaRepo
    @Binds
    abstract fun providesAnilistPagedMediaRepo(
        anilistPagedMediaRepository: AnilistPagedMediaRepository
    ): PagedMediaRepository

    @ExtensionPagedMediaRepo
    @Binds
    abstract fun providesExtensionPagedMediaRepo(
        extensionPagedMediaRepository: ExtensionPagedMediaRepository
    ): PagedMediaRepository
}