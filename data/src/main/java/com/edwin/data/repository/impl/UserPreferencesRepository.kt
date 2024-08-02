package com.edwin.data.repository.impl

import android.util.Log
import com.edwin.data.model.UserPreference
import com.edwin.network.userpref.UserPreferences
import com.edwin.network.userpref.UserPreferencesDataSource
import com.edwin.network.userpref.copy
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataSource: UserPreferencesDataSource
) {

    val userPreferencesData = dataSource.userPreferences.map { userPreference: UserPreferences ->
        UserPreference(
            repositoryUrls = userPreference.extensionRepositoryUrlsList
        )
    }

    suspend fun addRepositoryUrl(repoUrl: String) {
        try {
            dataSource.updateUserPref {
                it.copy {
                    extensionRepositoryUrls.add(repoUrl)
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun removeRepositoryUrl(repoUrl: String) {
        try {
            dataSource.updateUserPref { userPreferences ->
                userPreferences.copy {
                    extensionRepositoryUrls.filterNot { it == repoUrl }
                }
            }
        } catch (ioException: IOException) {
            Log.e("UserPreferences", "Failed to update user preferences", ioException)
        }
    }
}