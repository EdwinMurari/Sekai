package com.edwin.sekai.ui.feature.search.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.RightOverlayDialog
import com.edwin.sekai.ui.feature.search.model.FilterOption
import com.edwin.sekai.ui.feature.search.model.FilterScreen

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun FilterPopup(
    showDialog: Boolean,
    initialFilters: List<FilterOption<*>>,
    onFiltersChanged: (List<FilterOption<*>>) -> Unit,
    onResetFiltersClick: () -> Unit
) {
    val filters =
        remember { mutableStateListOf<FilterOption<*>>().apply { addAll(initialFilters) } }
    var currentFilterScreen by remember { mutableStateOf<FilterScreen>(FilterScreen.FilterList) }

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = { onFiltersChanged(filters) },
        title = { Text(stringResource(R.string.sort_and_filter)) },
        titleActionButton = {
            Button(
                content = { Text(stringResource(R.string.reset)) },
                onClick = onResetFiltersClick
            )
        },
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
                            contentPaddingValues = paddingValues,
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
                            contentPaddingValues = paddingValues,
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
private fun <T> SingleSelectFilterRow(
    filter: FilterOption.SingleSelect<T>,
    onFilterSelected: () -> Unit
) {
    ListItem(
        headlineContent = { Text(filter.filterType.title) },
        supportingContent = { Text(filter.selectedValue?.toString() ?: "") },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
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
private fun <T> MultiSelectFilterRow(
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
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
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