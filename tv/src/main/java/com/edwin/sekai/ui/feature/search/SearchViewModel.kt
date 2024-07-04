package com.edwin.sekai.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.edwin.data.model.Genre
import com.edwin.data.model.Media
import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams
import com.edwin.data.repository.MediaRepository
import com.edwin.sekai.ui.feature.search.component.Filter
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState(filters = emptyList()))
    val filterState = _filterState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<PagingData<Media>> = filterState
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { filterState ->
            searchQuery
                .debounce(300L)
                .distinctUntilChanged()
                .flatMapLatest { searchQuery ->
                    searchMedia(createSearchParamsFromFilters(searchQuery, filterState.filters))
                }
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    init {
        viewModelScope.launch {
            _filterState.value = FilterState(
                filters = createFiltersFromSearchParams(
                    searchParams = SearchParams(
                        page = 1,
                        perPage = 20
                    )
                )
            )
        }
    }

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun searchMedia(searchParams: SearchParams): Flow<PagingData<Media>> {
        return mediaRepository.getPagedSearchResults(searchParams)
    }

    fun onFiltersClick() {
        _filterState.update { it.copy(showFiltersDialog = true) }
    }

    fun onFiltersChanged(updatedFilters: List<Filter<*>>) {
        _filterState.value =
            filterState.value.copy(filters = updatedFilters, showFiltersDialog = false)
    }

    private fun createSearchParamsFromFilters(
        searchQuery: String,
        filters: List<Filter<*>>
    ): SearchParams {
        return filters.fold(
            initial = SearchParams(
                page = 1,
                perPage = 20,
                query = searchQuery.takeIf { it.isNotBlank() })
        ) { params, filter ->
            when (filter) {
                is Filter.SelectableFilter<*> -> {
                    when (filter.filterType) {
                        FilterType.FORMAT -> params.copy(format = filter.selectedValue as? MediaFormat)
                        FilterType.STATUS -> params.copy(status = filter.selectedValue as? MediaStatus)
                        FilterType.SORT_BY -> params.copy(sortBy = filter.selectedValue as? MediaSort)
                        FilterType.ORDER -> params.copy(
                            order = filter.selectedValue as? Order ?: Order.DESCENDING
                        )

                        FilterType.SEASON -> params.copy(season = filter.selectedValue as? MediaSeason)
                        FilterType.MIN_SCORE -> params.copy(minScore = filter.selectedValue as? Int)
                        FilterType.SEASON_YEAR -> params.copy(seasonYear = filter.selectedValue as? Int)
                        FilterType.IS_ADULT -> params.copy(isAdult = filter.selectedValue as? Boolean)
                        else -> params // Handle the else case appropriately
                    }
                }

                is Filter.MultiSelectableFilter<*> -> {
                    when (filter.filterType) {
                        FilterType.GENRES -> params.copy(
                            genres = (filter.selectedValue as? List<*>)
                                ?.filterIsInstance<Genre>()
                                ?.takeIf { it.isNotEmpty() }
                        )

                        else -> params // Handle the else case appropriately
                    }
                }
            }
        }
    }

    private fun createFiltersFromSearchParams(
        searchParams: SearchParams
    ): List<Filter<*>> = listOf(
        Filter.SelectableFilter(FilterType.FORMAT, searchParams.format, MediaFormat.entries),
        Filter.SelectableFilter(FilterType.STATUS, searchParams.status, MediaStatus.entries),
        Filter.SelectableFilter(FilterType.SORT_BY, searchParams.sortBy, MediaSort.entries),
        Filter.SelectableFilter(FilterType.ORDER, searchParams.order, Order.entries),
        Filter.MultiSelectableFilter(
            FilterType.GENRES,
            searchParams.genres.takeUnless { it.isNullOrEmpty() },
            Genre.entries
        ),
        Filter.SelectableFilter(FilterType.MIN_SCORE, searchParams.minScore, (1..10).toList()),
        Filter.SelectableFilter(
            FilterType.SEASON_YEAR,
            searchParams.seasonYear,
            (1950..2024).toList()
        ),
        Filter.SelectableFilter(FilterType.SEASON, searchParams.season, MediaSeason.entries),
        Filter.SelectableFilter(FilterType.IS_ADULT, searchParams.isAdult, listOf(true, false))
    )

    data class FilterState(
        val filters: List<Filter<*>>,
        val showFiltersDialog: Boolean = false
    )
}