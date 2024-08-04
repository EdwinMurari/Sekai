package com.edwin.data.repository.impl

import com.edwin.data.mapper.asExternalModel
import com.edwin.data.model.Media
import com.edwin.network.extensions.impl.ExtensionDataSource
import javax.inject.Inject

class ExtensionRepository @Inject constructor(
    private val extensionDataSource: ExtensionDataSource
) {

    suspend fun getPopularAnime(pkgName: String): List<Media>? {
        return extensionDataSource.fetchPopularAnime(pkgName, 1)?.asExternalModel()
    }
}