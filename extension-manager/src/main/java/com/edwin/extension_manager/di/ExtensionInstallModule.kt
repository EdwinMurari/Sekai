package com.edwin.extension_manager.di

import com.edwin.extension_manager.ExtensionInstallManager
import com.edwin.extension_manager.WorkManagerExtensionInstallManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ExtensionInstallModule {

    @Binds
    internal abstract fun bindsExtensionInstallManager(
        extensionInstallManager: WorkManagerExtensionInstallManager,
    ): ExtensionInstallManager
}