package com.edwin.sekai.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.data.repository.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<PagingData<Media>>(PagingData.empty())
    val uiState = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(300L) // Debounce to avoid too many requests while typing
                .distinctUntilChanged() // Only search when the query actually changes
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(PagingData.empty())
                    } else {
                        searchMedia(query)
                    }
                }
                .cachedIn(viewModelScope) // Important: Cache results in ViewModel scope
                .collect { pagingData ->
                    _uiState.value = pagingData
                }
        }
    }

    private fun searchMedia(query: String): Flow<PagingData<Media>> {
        return mediaRepository.getPagedSearchResults(
            SearchParams(
                page = 0,
                perPage = 20,
                query = query
            )
        )
    }
}