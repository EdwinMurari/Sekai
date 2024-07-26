package com.edwin.network.userpref.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.edwin.network.userpref.UserPreferences
import com.edwin.network.userpref.serializer.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesRecentSearchesDataStore(
        @ApplicationContext context: Context,
        serializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory.create(serializer = serializer) {
        context.dataStoreFile("user_preferences.pb")
    }
}