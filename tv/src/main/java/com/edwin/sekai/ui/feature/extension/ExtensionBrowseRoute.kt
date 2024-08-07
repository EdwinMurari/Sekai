package com.edwin.sekai.ui.feature.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.search.SearchScreen

@Composable
fun ExtensionBrowseRoute(
    modifier: Modifier = Modifier,
    onMediaClick: (Int) -> Unit,
    viewModel: ExtensionBrowseViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()

    SearchScreen(
        modifier = modifier,
        contentPaddingValues = PaddingValues(horizontal = 58.dp, vertical = 24.dp),
        pagingItems = lazyPagingItems,
        searchQuery = searchQuery,
        filterState = filterState,
        palettes = palettes,
        isTopBarVisible = false,
        onMediaClick = onMediaClick,
        updateTopBarVisibility = { },
        onSearchQueryChange = viewModel::onQueryChange,
        onFiltersClick = viewModel::onFiltersClick,
        onFiltersChanged = viewModel::onFiltersChanged,
        onResetFiltersClick = viewModel::resetFilters
    )
}