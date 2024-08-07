package com.edwin.data.repository.impl

import com.edwin.data.model.SourceSelectionStatus
import com.edwin.network.extensions.ExtensionDataSource
import com.edwin.network.extensions.ExtensionStateManager
import javax.inject.Inject

class SourceRepository @Inject constructor(
    private val extensionDataSource: ExtensionDataSource,
    private val extensionStateManager: ExtensionStateManager
) {
    suspend fun selectSource(packageName: String): SourceSelectionStatus {
        val result = extensionStateManager.updateSelectedSource(packageName) { pkgName ->
            extensionDataSource.getAnimeSourceFromPackage(pkgName)
        }

        return if (result.isSuccess) {
            SourceSelectionStatus.SourceSelected
        } else {
            SourceSelectionStatus.SourceUnavailable
        }
    }
}