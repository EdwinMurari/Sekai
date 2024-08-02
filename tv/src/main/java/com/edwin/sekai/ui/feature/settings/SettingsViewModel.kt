package com.edwin.sekai.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.UserPreference
import com.edwin.data.repository.impl.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val userPreferences: StateFlow<UserPreference> = userPreferencesRepository.userPreferencesData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreference(repositoryUrls = emptyList())
        )


    fun onClickAddRepository(repository: String) {
        viewModelScope.launch {
            userPreferencesRepository.addRepositoryUrl(repository)
        }
    }

    fun onClickRemoveRepository(repository: String) {
        viewModelScope.launch {
            userPreferencesRepository.removeRepositoryUrl(repository)
        }
    }
}