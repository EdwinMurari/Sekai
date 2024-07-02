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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _filterState = MutableStateFlow(FilterState())
    val filterState = _filterState.asStateFlow()

    val searchResults: StateFlow<PagingData<Media>> = filterState
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { filterState ->
            searchMedia(filterState.searchParams)
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun onQueryChange(query: String) {
        _filterState.update { it.copy(searchParams = it.searchParams.copy(query = query)) }
    }

    private fun searchMedia(searchParams: SearchParams): Flow<PagingData<Media>> {
        return mediaRepository.getPagedSearchResults(searchParams)
    }

    fun onFilterOverlayDismiss() {
        _filterState.update { it.copy(filterScreenState = null) }
    }

    fun onFilterTypeSelected(filterType: FilterType, value: Any?) {
        _filterState.update {
            it.copy(
                filterScreenState = FilterScreen.FilterOption(
                    filterType = filterType,
                    value = value
                )
            )
        }
    }

    fun onFilterParamChanged(filterType: FilterType, value: Any?) {
        _filterState.update {
            it.copy(searchParams = it.searchParams.updateFilter(filterType, value))
        }
    }

    data class FilterState(
        val searchParams: SearchParams = SearchParams(perPage = 20, page = 1),
        val filterScreenState: FilterScreen? = null
    )

    sealed interface FilterScreen {

        data class FilterOption(
            val filterType: FilterType,
            val value: Any? = null
        ) : FilterScreen
    }
}