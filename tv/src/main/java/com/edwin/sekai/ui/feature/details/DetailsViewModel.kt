package com.edwin.sekai.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.MediaDetails
import com.edwin.data.model.NetworkResponse
import com.edwin.sekai.domain.GetMediaDetailsByIdUseCase
import com.edwin.sekai.ui.feature.details.navigation.MediaDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMediaDetailsByIdUseCase: GetMediaDetailsByIdUseCase
) : ViewModel() {

    private val mediaDetailsArgs = MediaDetailsArgs(savedStateHandle)

    private val mediaId = mediaDetailsArgs.mediaId

    val mediaDetailsUiState: StateFlow<DetailsUiState> =
        getMediaDetailsByIdUseCase(mediaId).map { networkResponse ->
            when (networkResponse) {
                is NetworkResponse.Error -> DetailsUiState.Error
                is NetworkResponse.Success -> DetailsUiState.Success(networkResponse.data)
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailsUiState.Loading,
        )


    sealed class DetailsUiState {
        data object Loading : DetailsUiState()
        data class Success(val mediaDetails: MediaDetails) : DetailsUiState()
        data object Error : DetailsUiState()
    }
}