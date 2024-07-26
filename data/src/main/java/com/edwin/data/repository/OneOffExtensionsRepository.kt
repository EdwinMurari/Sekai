package com.edwin.data.repository

import com.edwin.data.model.Extension
import com.edwin.network.extensions.ExtensionsDataSource

class OneOffExtensionsRepository(
    private val extensionsDataSource: ExtensionsDataSource
) : ExtensionsRepository {

    override suspend fun getAvailableExtensions(): List<Extension.Available> {
        return emptyList()
    }
}