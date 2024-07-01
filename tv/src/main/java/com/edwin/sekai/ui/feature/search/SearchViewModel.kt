package com.edwin.sekai.ui.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.MediaDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.InitialState)
    val uiState = _uiState.asStateFlow()

    fun query(queryString: String) {
        viewModelScope.launch { postQuery(queryString) }
    }

    private suspend fun postQuery(queryString: String) {
        _uiState.emit(SearchUiState.Loading(queryString))
        val result = movieRepository.searchMovies(query = queryString)
        _uiState.emit(SearchUiState.Success(queryString, result))
    }

    sealed interface SearchUiState {
        val searchQuery: String

        data class Loading(override val searchQuery: String) : SearchUiState
        data object InitialState : SearchUiState {
            override val searchQuery: String get() = ""
        }

        data class Success(override val searchQuery: String, val mediaDetails: MediaDetails) :
            SearchUiState

        data class Error(override val searchQuery: String) : SearchUiState
    }
}