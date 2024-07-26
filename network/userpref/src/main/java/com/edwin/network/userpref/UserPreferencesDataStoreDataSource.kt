package com.edwin.network.userpref

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

internal class UserPreferencesDataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) : UserPreferencesDataSource {
    override val userPreferences: Flow<UserPreferences>
        get() = dataStore.data

    override suspend fun updateUserPref(updateData: (UserPreferences) -> UserPreferences) {
        try {
            dataStore.updateData(updateData)
        } catch (ioException: IOException) {
            Log.e(
                "UserPreferencesDataStoreDataSource",
                "Failed to update user preferences",
                ioException
            )
        }
    }
}