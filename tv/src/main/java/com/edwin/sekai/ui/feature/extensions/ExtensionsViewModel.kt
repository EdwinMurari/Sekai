package com.edwin.sekai.ui.feature.extensions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.Extension
import com.edwin.data.repository.ExtensionsRepository
import com.edwin.sekai.ui.feature.extensions.mapper.toUiModel
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ExtensionsViewModel @Inject constructor(
    private val repository: ExtensionsRepository
) : ViewModel() {

    val uiState: StateFlow<ExtensionsUiState> = repository.getAvailableExtensions()
        .map<List<Extension.Available>, ExtensionsUiState> { availableExtensionList ->
            ExtensionsUiState.Success(
                extensions = availableExtensionList.map(Extension.Available::toUiModel)
            )
        }
        .catch {
            Log.e("TEST", it.stackTraceToString())
            emit(ExtensionsUiState.Error)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExtensionsUiState.Loading,
        )
}

sealed interface ExtensionsUiState {

    data class Success(
        val extensions: List<ExtensionUiModel>
    ) : ExtensionsUiState

    data object Error : ExtensionsUiState
    data object Loading : ExtensionsUiState
}