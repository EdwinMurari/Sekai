package com.edwin.data.repository

import com.edwin.data.model.Extension

interface ExtensionsRepository {

    suspend fun getAvailableExtensions(): List<Extension.Available>
}