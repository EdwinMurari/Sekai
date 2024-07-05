package com.edwin.sekai.ui.feature.search.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.edwin.sekai.ui.feature.search.model.FilterPopupScreen

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
    val state = rememberFilterPopupState(initialFilters)

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = { onFiltersChanged(state.filters) },
        title = { modifier ->
            Text(
                text = stringResource(R.string.filter_popup_title),
                modifier = modifier
            )
        },
        titleActionButton = {
            Button(
                content = { Text(stringResource(R.string.clear)) },
                onClick = onResetFiltersClick
            )
        },
        content = { paddingValues ->
            AnimatedContent(
                targetState = state.currentScreen,
                label = "Filter popup content"
            ) { screen ->
                when (screen) {
                    FilterPopupScreen.FilterList -> {
                        FilterListContent(
                            contentPaddingValues = paddingValues,
                            filters = state.filters,
                            onFilterSelected = state::onFilterSelected
                        )
                    }

                    is FilterPopupScreen.FilterOptions -> {
                        FilterOptionContent(
                            contentPaddingValues = paddingValues,
                            filter = screen.filter,
                            onFilterOptionUpdated = { updatedFilter ->
                                state.updateFilter(updatedFilter)
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
private fun FilterListContent(
    contentPaddingValues: PaddingValues,
    filters: List<FilterOption<*>>,
    onFilterSelected: (FilterOption<*>) -> Unit
) {
    TvLazyColumn(contentPadding = contentPaddingValues) {
        items(filters, key = { it.filterType.ordinal }) { filter ->
            FilterTypeRow(
                filter = filter,
                onFilterSelected = { onFilterSelected(filter) },
                supportingContent = {
                    Text(
                        text = when (filter) {
                            is FilterOption.MultiSelect<*> -> filter.selectedValue
                                ?.joinToString(stringResource(R.string.comma_separator))
                                ?: ""

                            is FilterOption.SingleSelect -> filter.selectedValue
                                ?.toString()
                                ?: ""
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun <T> FilterTypeRow(
    filter: FilterOption<T>,
    supportingContent: @Composable () -> Unit,
    onFilterSelected: () -> Unit
) {
    ListItem(
        headlineContent = { Text(filter.filterType.title) },
        supportingContent = supportingContent,
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

@Composable
private fun FilterOptionContent(
    contentPaddingValues: PaddingValues,
    filter: FilterOption<*>,
    onFilterOptionUpdated: (FilterOption<*>) -> Unit
) {
    when (filter) {
        is FilterOption.MultiSelect<*> -> {
            MultiSelectFilterOptionContent(
                contentPaddingValues = contentPaddingValues,
                filter = filter,
                onFilterOptionSelected = onFilterOptionUpdated
            )
        }

        is FilterOption.SingleSelect -> {
            SingleSelectFilterOptionContent(
                contentPaddingValues = contentPaddingValues,
                filter = filter,
                onFilterOptionSelected = onFilterOptionUpdated
            )
        }
    }
}