package com.edwin.sekai.ui.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterState = MutableStateFlow(
        FilterState(
            filters = createFiltersFromSearchParams(
                searchParams = SearchParams(page = 1, perPage = 20),
                genres = emptyList(), // TODO :: Fetch genres from API
                tags = emptyList() // TODO :: Fetch tags from API
            )
        )
    )
    val filterState = _filterState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<PagingData<Media>> =
        combine(filterState, searchQuery) { filterState, searchQuery ->
            createSearchParamsFromFilters(searchQuery, filterState.filters)
        }
            .debounce(300L)
            .distinctUntilChanged()
            .flatMapLatest { searchParam ->
                searchMedia(searchParam)
            }
            .cachedIn(viewModelScope)
            .catch {
                Log.e("TEST", it.stackTraceToString())
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = PagingData.empty(),
                started = SharingStarted.WhileSubscribed(5_000),
            )

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
        var updatedParams = SearchParams(page = 1, perPage = 20, query = searchQuery)

        filters.forEach { filter ->
            when (filter) {
                is Filter.SelectableFilter<*> -> {
                    updatedParams = when (filter.filterType) {
                        FilterType.FORMAT -> updatedParams.copy(format = filter.selectedValue as? MediaFormat)
                        FilterType.STATUS -> updatedParams.copy(status = filter.selectedValue as? MediaStatus)
                        FilterType.SORT_BY -> updatedParams.copy(sortBy = filter.selectedValue as? MediaSort)
                        FilterType.ORDER -> updatedParams.copy(
                            order = filter.selectedValue as? Order ?: Order.DESCENDING
                        )

                        FilterType.SEASON -> updatedParams.copy(season = filter.selectedValue as? MediaSeason)
                        FilterType.MIN_SCORE -> updatedParams.copy(minScore = filter.selectedValue as? Int)
                        FilterType.SEASON_YEAR -> updatedParams.copy(seasonYear = filter.selectedValue as? Int)
                        FilterType.IS_ADULT -> updatedParams.copy(isAdult = filter.selectedValue as? Boolean)
                        else -> updatedParams
                    }
                }

                is Filter.MultiSelectableFilter<*> -> {
                    updatedParams = when (filter.filterType) {
                        FilterType.GENRES -> updatedParams.copy(genres = filter.selectedValue as? List<String>)
                        FilterType.TAGS -> updatedParams.copy(tags = filter.selectedValue as? List<String>)
                        else -> updatedParams
                    }
                }
            }
        }

        return updatedParams
    }

    private fun createFiltersFromSearchParams(
        searchParams: SearchParams,
        genres: List<String>,
        tags: List<String>
    ): List<Filter<*>> {
        return listOf<Filter<*>>(
            Filter.SelectableFilter(
                filterType = FilterType.FORMAT,
                selectedValue = searchParams.format,
                options = MediaFormat.entries
            ),
            Filter.SelectableFilter(
                filterType = FilterType.STATUS,
                selectedValue = searchParams.status,
                options = MediaStatus.entries
            ),
            Filter.SelectableFilter(
                filterType = FilterType.SORT_BY,
                selectedValue = searchParams.sortBy,
                options = MediaSort.entries
            ),
            Filter.SelectableFilter(
                filterType = FilterType.ORDER,
                selectedValue = searchParams.order,
                options = Order.entries
            ),
            Filter.MultiSelectableFilter(
                filterType = FilterType.GENRES,
                selectedValue = searchParams.genres ?: emptyList(),
                options = genres
            ),
            Filter.MultiSelectableFilter(
                filterType = FilterType.TAGS,
                selectedValue = searchParams.tags ?: emptyList(),
                options = tags
            ),
            Filter.SelectableFilter(
                filterType = FilterType.MIN_SCORE,
                selectedValue = searchParams.minScore,
                options = (1..10).toList()
            ),
            Filter.SelectableFilter(
                filterType = FilterType.SEASON_YEAR,
                selectedValue = searchParams.seasonYear,
                options = (1950..2024).toList()
            ),
            Filter.SelectableFilter(
                filterType = FilterType.SEASON,
                selectedValue = searchParams.season,
                options = MediaSeason.entries
            ),
            Filter.SelectableFilter(
                filterType = FilterType.IS_ADULT,
                selectedValue = searchParams.isAdult,
                options = listOf(true, false)
            )
        )
    }

    data class FilterState(
        val filters: List<Filter<*>>,
        val showFiltersDialog: Boolean = false
    )
}