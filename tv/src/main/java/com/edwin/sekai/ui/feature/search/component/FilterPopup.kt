package com.edwin.sekai.ui.feature.search.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Checkbox
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.RadioButton
import androidx.tv.material3.Text
import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams
import com.edwin.sekai.ui.designsystem.component.RightOverlayDialog
import com.edwin.sekai.ui.feature.search.FilterType

@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FilterPopup(
    showDialog: Boolean,
    searchParams: SearchParams,
    genres: List<String>,
    tags: List<String>,
    onFiltersChanged: (SearchParams) -> Unit
) {
    val (filters, updateFilters) = remember(searchParams) {
        mutableStateOf(
            createFilterCategories(
                searchParams = searchParams,
                genres = genres,
                tags = tags
            )
        )
    }
    val (screenState, setScreenState) = remember {
        mutableStateOf<FilterScreenState>(
            FilterScreenState.Filters
        )
    }

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = { onFiltersChanged(createSearchParamsFromFilters(filters)) },
        title = {},
        titleActionButton = {},
        content = { paddingValues ->
            AnimatedContent(screenState, label = "") { state ->
                when (state) {
                    FilterScreenState.Filters -> {
                        FiltersContent(
                            contentPaddingValues = paddingValues,
                            filters = filters,
                            onFilterSelected = {
                                val newScreenState = when (it) {
                                    is Filter.MultiSelectableFilter<*> -> FilterScreenState.MultiFilterOption(
                                        it
                                    )

                                    is Filter.SelectableFilter -> FilterScreenState.SelectFilterOption(
                                        it
                                    )
                                }
                                setScreenState(newScreenState)
                            }
                        )
                    }

                    is FilterScreenState.MultiFilterOption<*> -> {
                        MultiSelectFilterOptionContent(
                            filter = state.filter,
                            onFilterOptionSelected = { updatedFilter ->
                                updateFilters(
                                    filters.map {
                                        if (it.filterType == state.filter.filterType) updatedFilter else it
                                    }
                                )
                            }
                        )
                    }

                    is FilterScreenState.SelectFilterOption<*> -> {
                        SingleSelectFilterOptionContent(
                            filter = state.filter,
                            onFilterOptionSelected = {}
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> SingleSelectFilterOptionContent(
    filter: Filter.SelectableFilter<T>,
    onFilterOptionSelected: (Filter.SelectableFilter<T>) -> Unit,
) {
    TvLazyColumn {
        items(filter.options) { option ->
            ListItem(
                headlineContent = { Text(option.toString()) },
                trailingContent = {
                    RadioButton(
                        selected = filter.selectedValue == option,
                        onClick = null
                    )
                },
                onClick = { onFilterOptionSelected(filter.copy(selectedValue = option)) },
                selected = false
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> MultiSelectFilterOptionContent(
    filter: Filter.MultiSelectableFilter<T>,
    onFilterOptionSelected: (Filter.MultiSelectableFilter<T>) -> Unit,
) {
    TvLazyColumn {
        items(filter.options) { option ->
            ListItem(
                headlineContent = { Text(option.toString()) },
                onClick = { onFilterOptionSelected(filter.copy(selectedValue = listOf(option))) },
                trailingContent = {
                    Checkbox(
                        checked = filter.selectedValue?.contains(option) == true,
                        onCheckedChange = null
                    )
                },
                selected = false
            )
        }
    }
}

@Composable
private fun FiltersContent(
    contentPaddingValues: PaddingValues,
    filters: List<Filter<*>>,
    onFilterSelected: (Filter<*>) -> Unit
) {
    TvLazyColumn(contentPadding = contentPaddingValues) {
        items(filters) { filter ->
            when (filter) {
                is Filter.SelectableFilter<*> -> {
                    SelectableFilterRow(
                        filter = filter,
                        onFilterSelected = { onFilterSelected(filter) }
                    )
                }

                is Filter.MultiSelectableFilter<*> -> {
                    MultiSelectableFilterRow(
                        filter = filter,
                        onFilterSelected = { onFilterSelected(filter) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> SelectableFilterRow(
    filter: Filter.SelectableFilter<T>,
    onFilterSelected: () -> Unit
) {
    ListItem(
        headlineContent = { Text(filter.filterType.title) },
        supportingContent = { Text(filter.selectedValue?.toString() ?: "") },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Select Filter"
            )
        },
        leadingContent = {
            Icon(
                imageVector = filter.filterType.icon,
                contentDescription = "${filter.filterType.title} Icon"
            )
        },
        onClick = onFilterSelected,
        selected = false
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> MultiSelectableFilterRow(
    filter: Filter.MultiSelectableFilter<T>,
    onFilterSelected: () -> Unit
) {
    ListItem(
        headlineContent = { Text(filter.filterType.title) },
        supportingContent = { Text(filter.selectedValue?.joinToString(", ") ?: "") },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Select Filter"
            )
        },
        leadingContent = {
            Icon(
                imageVector = filter.filterType.icon,
                contentDescription = "${filter.filterType.title} Icon"
            )
        },
        onClick = onFilterSelected,
        selected = false
    )
}

sealed interface Filter<T> {
    val filterType: FilterType
    val selectedValue: T?

    data class SelectableFilter<T>(
        override val filterType: FilterType,
        override val selectedValue: T?,
        val options: List<T>
    ) : Filter<T>

    data class MultiSelectableFilter<T>(
        override val filterType: FilterType,
        override val selectedValue: List<T>?,
        val options: List<T>
    ) : Filter<List<T>>
}

sealed interface FilterScreenState {
    data object Filters : FilterScreenState
    data class SelectFilterOption<T>(val filter: Filter.SelectableFilter<T>) : FilterScreenState
    data class MultiFilterOption<T>(val filter: Filter.MultiSelectableFilter<T>) : FilterScreenState
}

private fun createSearchParamsFromFilters(
    filters: List<Filter<*>>
): SearchParams {
    var updatedParams = SearchParams(page = 1, perPage = 20)

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
                    else -> updatedParams // Ignore other MultiSelectableFilters
                }
            }
        }
    }

    return updatedParams
}

fun createFilterCategories(
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