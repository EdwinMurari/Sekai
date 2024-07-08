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
import com.edwin.sekai.ui.feature.search.model.FilterOption
import com.edwin.sekai.ui.feature.search.model.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val initialFilterState = createFiltersFromSearchParams(
        searchParams = SearchParams(
            page = 1,
            perPage = 20
        )
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState(filters = initialFilterState))
    val filterState = _filterState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<PagingData<Media>> = combine(
        flow = searchQuery,
        flow2 = filterState,
        transform = ::buildSearchParams
    )
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest(transform = ::searchMedia)
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty()
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

    fun onFiltersChanged(updatedFilters: List<FilterOption<*>>) {
        _filterState.value =
            filterState.value.copy(filters = updatedFilters, showFiltersDialog = false)
    }

    fun resetFilters() {
        _filterState.value =
            filterState.value.copy(filters = initialFilterState, showFiltersDialog = false)
    }

    private fun buildSearchParams(
        searchQuery: String,
        filterState: FilterState
    ): SearchParams {
        return filterState.filters.fold(
            initial = SearchParams(
                page = 1,
                perPage = 20,
                query = searchQuery.takeIf { it.isNotBlank() }
            )
        ) { params, filter ->
            when (filter) {
                is FilterOption.SingleSelect<*> -> {
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

                is FilterOption.MultiSelect<*> -> {
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
    ): List<FilterOption<*>> = FilterType.entries.map { filterType ->
        when (filterType) {
            FilterType.FORMAT -> FilterOption.SingleSelect(
                filterType = FilterType.FORMAT,
                selectedValue = searchParams.format,
                options = MediaFormat.entries
            )

            FilterType.STATUS -> FilterOption.SingleSelect(
                filterType = FilterType.STATUS,
                selectedValue = searchParams.status,
                options = MediaStatus.entries
            )

            FilterType.SORT_BY -> FilterOption.SingleSelect(
                filterType = FilterType.SORT_BY,
                selectedValue = searchParams.sortBy,
                options = MediaSort.entries
            )

            FilterType.ORDER -> FilterOption.SingleSelect(
                filterType = FilterType.ORDER,
                selectedValue = searchParams.order,
                options = Order.entries
            )

            FilterType.GENRES -> FilterOption.MultiSelect(
                filterType = FilterType.GENRES,
                selectedValue = searchParams.genres.takeUnless { it.isNullOrEmpty() },
                options = Genre.entries
            )

            FilterType.MIN_SCORE -> FilterOption.SingleSelect(
                filterType = FilterType.MIN_SCORE,
                selectedValue = searchParams.minScore,
                options = (1..10).toList()
            )

            FilterType.SEASON_YEAR -> FilterOption.SingleSelect(
                filterType = FilterType.SEASON_YEAR,
                selectedValue = searchParams.seasonYear,
                options = (LocalDate.now().year downTo 1940).toList()
            )

            FilterType.SEASON -> FilterOption.SingleSelect(
                filterType = FilterType.SEASON,
                selectedValue = searchParams.season,
                options = MediaSeason.entries
            )

            FilterType.IS_ADULT -> FilterOption.SingleSelect(
                filterType = FilterType.IS_ADULT,
                selectedValue = searchParams.isAdult,
                options = listOf(true, false)
            )
        }
    }

    data class FilterState(
        val filters: List<FilterOption<*>> = emptyList(),
        val showFiltersDialog: Boolean = false
    )
}