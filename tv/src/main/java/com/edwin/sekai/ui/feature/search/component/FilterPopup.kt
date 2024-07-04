package com.edwin.sekai.ui.feature.search.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    ExperimentalTvMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FilterPopup(
    showDialog: Boolean,
    initialFilters: List<FilterOption<*>>,
    onFiltersChanged: (List<FilterOption<*>>) -> Unit
) {
    val filters =
        remember { mutableStateListOf<FilterOption<*>>().apply { addAll(initialFilters) } }
    var currentFilterScreen by remember { mutableStateOf<FilterScreen>(FilterScreen.FilterList) }

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = { onFiltersChanged(filters) },
        title = {},
        titleActionButton = {},
        content = { paddingValues ->
            AnimatedContent(currentFilterScreen, label = "") { screen ->
                when (screen) {
                    FilterScreen.FilterList -> {
                        FilterListContent(
                            contentPaddingValues = paddingValues,
                            filters = filters,
                            onFilterSelected = { filter ->
                                currentFilterScreen = when (filter) {
                                    is FilterOption.MultiSelect<*> ->
                                        FilterScreen.MultiSelectOptions(filter)

                                    is FilterOption.SingleSelect ->
                                        FilterScreen.SingleSelectOptions(filter)
                                }
                            }
                        )
                    }

                    is FilterScreen.MultiSelectOptions<*> -> {
                        MultiSelectFilterOptionContent(
                            filter = screen.filter,
                            onFilterOptionSelected = { updatedFilter ->
                                val index =
                                    filters.indexOfFirst { it.filterType == updatedFilter.filterType }
                                if (index != -1) {
                                    filters[index] = updatedFilter
                                }
                                currentFilterScreen = FilterScreen.FilterList
                            }
                        )
                    }

                    is FilterScreen.SingleSelectOptions<*> -> {
                        SingleSelectFilterOptionContent(
                            filter = screen.filter,
                            onFilterOptionSelected = { updatedFilter ->
                                val index =
                                    filters.indexOfFirst { it.filterType == updatedFilter.filterType }
                                if (index != -1) {
                                    filters[index] = updatedFilter
                                }
                                currentFilterScreen = FilterScreen.FilterList
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
    filter: FilterOption.SingleSelect<T>,
    onFilterOptionSelected: (FilterOption.SingleSelect<T>) -> Unit,
) {
    BackHandler { onFilterOptionSelected(filter) }

    TvLazyColumn {
        items(filter.options) { option ->
            ListItem(
                headlineContent = { Text(option.toString()) },
                trailingContent = {
                    RadioButton(
                        selected = filter.selectedValue == option,
                        enabled = true,
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
    filter: FilterOption.MultiSelect<T>,
    onFilterOptionSelected: (FilterOption.MultiSelect<T>) -> Unit,
) {
    val currentSelection = remember {
        mutableStateListOf<T>().apply {
            filter.selectedValue?.let { addAll(it) }
        }
    }

    BackHandler { onFilterOptionSelected(filter.copy(selectedValue = currentSelection.toList())) }

    TvLazyColumn {
        items(filter.options) { option ->
            ListItem(
                headlineContent = { Text(option.toString()) },
                onClick = {
                    if (currentSelection.contains(option)) {
                        currentSelection.remove(option)
                    } else {
                        currentSelection.add(option)
                    }
                },
                trailingContent = {
                    Checkbox(
                        checked = currentSelection.contains(option),
                        enabled = true,
                        onCheckedChange = null
                    )
                },
                selected = false
            )
        }
    }
}

@Composable
private fun FilterListContent(
    contentPaddingValues: PaddingValues,
    filters: List<FilterOption<*>>,
    onFilterSelected: (FilterOption<*>) -> Unit
) {
    TvLazyColumn(contentPadding = contentPaddingValues) {
        items(filters) { filter ->
            when (filter) {
                is FilterOption.SingleSelect<*> -> {
                    SingleSelectFilterRow(
                        filter = filter,
                        onFilterSelected = { onFilterSelected(filter) }
                    )
                }

                is FilterOption.MultiSelect<*> -> {
                    MultiSelectFilterRow(
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
fun <T> SingleSelectFilterRow(
    filter: FilterOption.SingleSelect<T>,
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
fun <T> MultiSelectFilterRow(
    filter: FilterOption.MultiSelect<T>,
    onFilterSelected: () -> Unit
) {
    ListItem(
        headlineContent = { Text(filter.filterType.title) },
        supportingContent = {
            val selectedCount = filter.selectedValue?.size ?: 0
            Text(if (selectedCount > 0) "$selectedCount selected" else "")
        },
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

sealed interface FilterOption<T> {
    val filterType: FilterType
    val selectedValue: T?

    data class SingleSelect<T>(
        override val filterType: FilterType,
        override val selectedValue: T?,
        val options: List<T>
    ) : FilterOption<T>

    data class MultiSelect<T>(
        override val filterType: FilterType,
        override val selectedValue: List<T>?,
        val options: List<T>
    ) : FilterOption<List<T>>
}

sealed interface FilterScreen {
    data object FilterList : FilterScreen
    data class SingleSelectOptions<T>(val filter: FilterOption.SingleSelect<T>) : FilterScreen
    data class MultiSelectOptions<T>(val filter: FilterOption.MultiSelect<T>) : FilterScreen
}