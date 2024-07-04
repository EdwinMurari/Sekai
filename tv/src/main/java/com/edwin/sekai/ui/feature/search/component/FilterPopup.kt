package com.edwin.sekai.ui.feature.search.component

import androidx.activity.compose.BackHandler
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
import com.edwin.sekai.ui.designsystem.component.RightOverlayDialog
import com.edwin.sekai.ui.feature.search.FilterType

@OptIn(
    ExperimentalTvMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FilterPopup(
    showDialog: Boolean,
    initialFilters: List<Filter<*>>,
    onFiltersChanged: (List<Filter<*>>) -> Unit
) {
    val (filters, updateFilters) = remember { mutableStateOf(initialFilters) }

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = { onFiltersChanged(filters) },
        title = {},
        titleActionButton = {},
        content = { paddingValues ->
            val (screenState, setScreenState) = remember {
                mutableStateOf<FilterScreenState>(
                    FilterScreenState.Filters
                )
            }

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
                        BackHandler { setScreenState(FilterScreenState.Filters) }

                        MultiSelectFilterOptionContent(
                            filter = state.filter,
                            onFilterOptionSelected = { updatedFilter ->
                                updateFilters(
                                    filters.map {
                                        if (it.filterType == state.filter.filterType) updatedFilter else it
                                    }
                                )
                                setScreenState(FilterScreenState.Filters)
                            }
                        )
                    }

                    is FilterScreenState.SelectFilterOption<*> -> {
                        BackHandler { setScreenState(FilterScreenState.Filters) }

                        SingleSelectFilterOptionContent(
                            filter = state.filter,
                            onFilterOptionSelected = { updatedFilter ->
                                updateFilters(
                                    filters.map {
                                        if (it.filterType == state.filter.filterType) updatedFilter else it
                                    }
                                )
                                setScreenState(FilterScreenState.Filters)
                            }
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