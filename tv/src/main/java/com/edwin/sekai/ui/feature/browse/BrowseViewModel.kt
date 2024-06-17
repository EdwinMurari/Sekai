package com.edwin.sekai.ui.feature.browse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.MediaCollections
import com.edwin.data.model.NetworkResponse
import com.edwin.sekai.domain.GetTrendingAnimeForCurrentSeasonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getTrendingAnimeForCurrentSeasonUseCase: GetTrendingAnimeForCurrentSeasonUseCase
) : ViewModel() {

    val uiState: StateFlow<BrowseScreenUiState> =
        getTrendingAnimeForCurrentSeasonUseCase().map { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.Failure -> BrowseScreenUiState.Error
                is NetworkResponse.Success -> BrowseScreenUiState.Success(networkResponse.data)
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = BrowseScreenUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    sealed class BrowseScreenUiState {
        data object Loading : BrowseScreenUiState()
        data class Success(val collection: MediaCollections) : BrowseScreenUiState()
        data object Error : BrowseScreenUiState()
    }
}