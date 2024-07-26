package com.edwin.network.userpref

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {

    val userPreferences: Flow<UserPreferences>

    suspend fun updateUserPref(updateData: (UserPreferences) -> UserPreferences)
}