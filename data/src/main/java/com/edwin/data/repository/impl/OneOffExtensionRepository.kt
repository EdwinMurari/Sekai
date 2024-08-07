package com.edwin.data.repository.impl

import com.edwin.data.model.SourceSelectionStatus
import com.edwin.network.extensions.ExtensionDataSource
import com.edwin.network.extensions.impl.ExtensionStateManager
import javax.inject.Inject

class ExtensionRepository @Inject constructor(
    private val extensionDataSource: ExtensionDataSource
) {
    suspend fun selectSource(packageName: String): SourceSelectionStatus {
        val result = ExtensionStateManager.updateSelectedSource(packageName) { pkgName ->
            extensionDataSource.getAnimeSourceFromPackage(pkgName)
        }

        return if (result.isSuccess) {
            SourceSelectionStatus.SourceSelected
        } else {
            SourceSelectionStatus.SourceUnavailable
        }
    }
}