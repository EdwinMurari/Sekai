package com.edwin.network.common.di

import com.edwin.network.extensions.ExtensionsDataSource
import com.edwin.network.extensions.ExtensionsNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExtensionsDataSourceModule {

    @Binds
    internal abstract fun bindsExtensionsDataSource(
        dataSource: ExtensionsNetworkDataSource
    ): ExtensionsDataSource
}