package com.edwin.data.repository.impl

import android.content.Context
import com.edwin.data.mapper.mapExtensionExtensionAsExternalModel
import com.edwin.data.model.Extension
import com.edwin.data.repository.ExtensionsRepository
import com.edwin.network.extensions.ExtensionsDataSource
import com.edwin.network.extensions.InstalledExtensionsDataSource
import com.edwin.network.userpref.UserPreferences
import com.edwin.network.userpref.UserPreferencesDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import javax.inject.Inject

internal class OneOffExtensionsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val availableExtensionsDataSource: ExtensionsDataSource,
    private val installedExtensionsDataSource: InstalledExtensionsDataSource,
    private val userPreferencesDataSource: UserPreferencesDataSource
) : ExtensionsRepository {

    override fun getExtensions(): Flow<List<Extension>> {
        return userPreferencesDataSource.userPreferences.map { userPreferences: UserPreferences ->
            userPreferences.extensionRepositoryUrlsList.flatMap { repositoryUrl ->
                when {
                    repositoryUrl.matches(REPO_REGEX.toRegex()) -> {
                        val installedExtensions =
                            installedExtensionsDataSource.getInstalledExtensions()
                        val availableExtensions =
                            availableExtensionsDataSource.getGithubExtensions(repositoryUrl)

                        mapExtensionExtensionAsExternalModel(
                            context = context,
                            installedExtensions = installedExtensions,
                            availableExtensions = availableExtensions,
                            repositoryUrl = repositoryUrl
                        )
                    }

                    else -> emptyList()
                }
            }
        }
    }

    override suspend fun downloadExtensionApk(apkUrl: String): ResponseBody {
        return availableExtensionsDataSource.downloadExtensionApk(apkUrl)
    }

    companion object {
        private const val REPO_REGEX = """^https://.*/index\.min\.json$"""

        private const val LIB_VERSION_MIN = 12.0
        private const val LIB_VERSION_MAX = 15.0
    }
}