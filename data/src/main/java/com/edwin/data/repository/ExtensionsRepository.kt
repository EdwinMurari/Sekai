package com.edwin.data.repository

import com.edwin.data.model.Extension
import kotlinx.coroutines.flow.Flow

interface ExtensionsRepository {

    fun getAvailableExtensions(): Flow<List<Extension.Available>>
}