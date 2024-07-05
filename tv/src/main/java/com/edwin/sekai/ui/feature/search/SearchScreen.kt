package com.edwin.sekai.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvGridItemSpan
import androidx.tv.foundation.lazy.grid.TvLazyGridScope
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.InputChip
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.MediaCard
import com.edwin.sekai.ui.designsystem.component.MediaCardPlaceholder
import com.edwin.sekai.ui.designsystem.component.SearchTextField
import com.edwin.sekai.ui.feature.search.component.FilterPopup
import com.edwin.sekai.ui.feature.search.model.FilterOption

// Constants
private const val LOADING_CONTENT_TYPE = "LoadingContentType"
private const val ERROR_CONTENT_TYPE = "ErrorContentType"
private const val SEARCH_RESULT_ITEM_CONTENT_TYPE = "SearchResultItemContentType"
private const val SEARCH_HEADER_CONTENT_TYPE = "SearchHeaderContentType"

@Composable
fun SearchRoute(
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()

    SearchScreen(
        modifier = modifier,
        pagingItems = lazyPagingItems,
        searchQuery = searchQuery,
        filterState = filterState,
        palettes = palettes,
        onMediaClick = onMediaClick,
        onSearchQueryChange = viewModel::onQueryChange,
        onFiltersClick = viewModel::onFiltersClick,
        onFiltersChanged = viewModel::onFiltersChanged,
        onResetFiltersClick = viewModel::resetFilters
    )
}

@Composable
fun SearchScreen(
    pagingItems: LazyPagingItems<Media>,
    searchQuery: String,
    filterState: SearchViewModel.FilterState,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit = {},
    onMediaClick: (Int) -> Unit = {},
    onFiltersClick: () -> Unit = {},
    onFiltersChanged: (List<FilterOption<*>>) -> Unit = {},
    onResetFiltersClick: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        SearchContent(
            modifier = Modifier.fillMaxSize(),
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            pagingItems = pagingItems,
            palettes = palettes,
            onMediaClick = onMediaClick,
            onFiltersClick = onFiltersClick
        )

        FilterPopup(
            showDialog = filterState.showFiltersDialog,
            initialFilters = filterState.filters,
            onFiltersChanged = onFiltersChanged,
            onResetFiltersClick = onResetFiltersClick
        )
    }
}

@Composable
private fun SearchContent(
    modifier: Modifier,
    searchQuery: String,
    pagingItems: LazyPagingItems<Media>,
    palettes: Map<String, Material3Palette>,
    onSearchQueryChange: (String) -> Unit,
    onMediaClick: (Int) -> Unit,
    onFiltersClick: () -> Unit
) {
    TvLazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(56.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = TvGridCells.Adaptive(minSize = 156.dp)
    ) {
        searchHeader(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onFiltersClick = onFiltersClick
        )

        refreshStateItem(pagingItems = pagingItems)

        searchResultItems(
            pagingItems = pagingItems,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        appendStateItem(pagingItems = pagingItems)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
private fun TvLazyGridScope.searchHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFiltersClick: () -> Unit
) {
    item(span = { TvGridItemSpan(maxLineSpan) }, contentType = { SEARCH_HEADER_CONTENT_TYPE }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                modifier = Modifier.weight(1f),
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )

            InputChip(
                selected = false,
                onClick = onFiltersClick
            ) {
                Text(
                    text = stringResource(R.string.sort_and_filter),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

private fun TvLazyGridScope.searchResultItems(
    pagingItems: LazyPagingItems<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    items(
        count = pagingItems.itemCount,
        key = pagingItems.itemKey { it.id },
        contentType = { SEARCH_RESULT_ITEM_CONTENT_TYPE }
    ) { index ->
        val media = pagingItems[index]
        if (media != null) {
            MediaCard(
                media = media,
                palettes = palettes,
                onClick = onMediaClick
            )
        } else {
            MediaCardPlaceholder()
        }
    }
}

private fun TvLazyGridScope.refreshStateItem(pagingItems: LazyPagingItems<Media>) {
    when (pagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            item(span = { TvGridItemSpan(maxLineSpan) }, contentType = LOADING_CONTENT_TYPE) {
                LoadingItem()
            }
        }

        is LoadState.Error -> {
            val error = pagingItems.loadState.refresh as LoadState.Error
            item(span = { TvGridItemSpan(maxLineSpan) }, contentType = ERROR_CONTENT_TYPE) {
                ErrorItem(errorMessage = error.error.message ?: "Unknown Error")
            }
        }

        else -> {} // Do nothing in LoadState.NotLoading for initial load
    }
}

private fun TvLazyGridScope.appendStateItem(pagingItems: LazyPagingItems<Media>) {
    when (pagingItems.loadState.append) {
        is LoadState.Loading -> {
            item(contentType = LOADING_CONTENT_TYPE) {
                LoadingItem()
            }
        }

        is LoadState.Error -> {
            val error = pagingItems.loadState.refresh as LoadState.Error
            item(span = { TvGridItemSpan(maxCurrentLineSpan) }, contentType = ERROR_CONTENT_TYPE) {
                ErrorItem(errorMessage = error.error.message ?: "Unknown Error")
            }
        }

        else -> {} // No need to do anything in LoadState.NotLoading for append
    }
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ErrorItem(errorMessage: String, modifier: Modifier = Modifier) {
    Text(
        text = "Error: $errorMessage",
        modifier = modifier.padding(16.dp)
    )
}