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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Media>>(PagingData.empty())
    val uiState = _uiState.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState = _filterState.asStateFlow()

    init {
        viewModelScope.launch {
            filterState
                .debounce(300L)
                .distinctUntilChanged()
                .flatMapLatest { filterState ->
                    searchMedia(filterState.searchParams)
                }
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.value = pagingData
                }
        }
    }

    fun onQueryChange(query: String) {
        _filterState.update { it.copy(searchParams = it.searchParams.copy(query = query)) }
    }

    private fun searchMedia(searchParams: SearchParams): Flow<PagingData<Media>> {
        return mediaRepository.getPagedSearchResults(searchParams)
    }

    fun onFilterClick() {
        _filterState.update { it.copy(showFilterOverlay = true) }
    }

    fun onFilterOverlayDismiss() {
        _filterState.update { it.copy(showFilterOverlay = false) }
    }

    fun onFilterParamsChanged(newSearchParams: SearchParams) {
        _filterState.update { it.copy(searchParams = newSearchParams) }
    }

    data class FilterState(
        val searchParams: SearchParams = SearchParams(perPage = 20, page = 1),
        val showFilterOverlay: Boolean = false
    )
}