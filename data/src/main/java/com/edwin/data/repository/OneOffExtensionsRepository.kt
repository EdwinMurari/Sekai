package com.edwin.data.repository

import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.extractLibVersion
import com.edwin.data.model.Extension
import com.edwin.network.extensions.ExtensionsDataSource
import com.edwin.network.userpref.UserPreferences
import com.edwin.network.userpref.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OneOffExtensionsRepository @Inject constructor(
    private val extensionsDataSource: ExtensionsDataSource,
    private val userPreferencesDataSource: UserPreferencesDataSource
) : ExtensionsRepository {

    override fun getAvailableExtensions(): Flow<List<Extension.Available>> {
        return userPreferencesDataSource.userPreferences.map { userPreferences: UserPreferences ->
            userPreferences.extensionRepositoryUrlsList.flatMap { repositoryUrl ->
                when {
                    repositoryUrl.matches(REPO_REGEX.toRegex()) -> {
                        extensionsDataSource.getGithubExtensions(repositoryUrl)
                            .filter {
                                it.extractLibVersion() in LIB_VERSION_MIN..LIB_VERSION_MAX
                            }
                            .map {
                                it.asExternalModel(repositoryUrl)
                            }
                    }

                    else -> emptyList()
                }
            }
        }
    }

    companion object {
        private const val REPO_REGEX = """^https://.*/index\.min\.json$"""


        private const val LIB_VERSION_MIN = 12.0
        private const val LIB_VERSION_MAX = 15.0
    }
}