package com.edwin.sekai.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvGridItemSpan
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.InputChip
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.MediaCard
import com.edwin.sekai.ui.designsystem.component.MediaCardPlaceholder
import com.edwin.sekai.ui.designsystem.component.SearchTextField
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.previewprovider.MediaListPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.search.component.Filter
import com.edwin.sekai.ui.feature.search.component.FilterPopup
import kotlinx.coroutines.flow.MutableStateFlow

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
        pagingItems = lazyPagingItems,
        searchQuery = searchQuery,
        filterState = filterState,
        palettes = palettes,
        onMediaClick = onMediaClick,
        onSearchQueryChange = viewModel::onQueryChange,
        onFiltersClick = viewModel::onFiltersClick,
        onFiltersChanged = viewModel::onFiltersChanged,
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    pagingItems: LazyPagingItems<Media>,
    searchQuery: String,
    filterState: SearchViewModel.FilterState,
    palettes: Map<String, Material3Palette>,
    onSearchQueryChange: (String) -> Unit,
    onMediaClick: (Int) -> Unit,
    onFiltersClick: () -> Unit,
    onFiltersChanged: (List<Filter<*>>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        SearchContent(
            modifier = modifier,
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
            onFiltersChanged = onFiltersChanged
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
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
        item(span = { TvGridItemSpan(maxLineSpan) }) {
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
                        text = "Filters",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { index ->
            val media = pagingItems[index]
            if (media != null) {
                MediaCard(
                    media = media,
                    palettes = palettes,
                    onClick = onMediaClick
                )
            } else {
                MediaCardPlaceholder(palettes = palettes)
            }
        }
    }
}

@TvPreview
@Composable
fun PreviewSearchScreen(
    @PreviewParameter(MediaListPreviewParameterProvider::class) mediaList: List<Media>
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
    val pagingData = PagingData.from(mediaList)
    val fakeDataFlow = MutableStateFlow(pagingData)
    val lazyPagingItems = fakeDataFlow.collectAsLazyPagingItems()

    SekaiTheme {
        SearchScreen(
            modifier = Modifier,
            onSearchQueryChange = setSearchQuery,
            filterState = SearchViewModel.FilterState(filters = emptyList(), showFiltersDialog = false),
            searchQuery = searchQuery,
            pagingItems = lazyPagingItems,
            palettes = palettes,
            onMediaClick = {},
            onFiltersClick = {},
            onFiltersChanged = {},
        )
    }
}