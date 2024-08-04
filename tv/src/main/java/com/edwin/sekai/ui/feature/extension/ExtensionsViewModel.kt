package com.edwin.sekai.ui.feature.extension

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.Media
import com.edwin.data.repository.impl.ExtensionRepository
import com.edwin.sekai.ui.feature.extension.navigation.ExtensionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtensionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ExtensionRepository
) : ViewModel() {

    private val extensionArgs = ExtensionArgs(savedStateHandle)

    private val pkgName = extensionArgs.pkgName

    private val _uiState = MutableStateFlow<ExtensionUiState>(ExtensionUiState.Loading)
    val uiState: StateFlow<ExtensionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.getPopularAnime(pkgName)?.let {
                    _uiState.value = ExtensionUiState.Success(it)
                } ?: run {
                    _uiState.value = ExtensionUiState.Error
                }
            } catch (e: Exception) {
                Log.e("TEST", e.stackTraceToString())
                _uiState.value = ExtensionUiState.Error
            }
        }
    }
}

sealed interface ExtensionUiState {

    data class Success(
        val media: List<Media>
    ) : ExtensionUiState

    data object Error : ExtensionUiState
    data object Loading : ExtensionUiState
}