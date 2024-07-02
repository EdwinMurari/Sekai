package com.edwin.sekai.ui.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
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
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.FilterChip
import androidx.tv.material3.Icon
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
import com.edwin.sekai.ui.feature.search.component.FilterSelectionPopup
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchRoute(
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.uiState.collectAsLazyPagingItems()

    SearchScreen(
        pagingItems = lazyPagingItems,
        filterState = filterState,
        palettes = palettes,
        onMediaClick = onMediaClick,
        onFilterClick = viewModel::onFilterClick,
        onFilterDismiss = viewModel::onFilterOverlayDismiss,
        onSearchQueryChange = viewModel::onQueryChange,
        onFilterParamsChanged = viewModel::onFilterParamsChanged,
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    pagingItems: LazyPagingItems<Media>,
    filterState: SearchViewModel.FilterState,
    palettes: Map<String, Material3Palette>,
    onSearchQueryChange: (String) -> Unit,
    onMediaClick: (Int) -> Unit,
    onFilterClick: () -> Unit,
    onFilterDismiss: () -> Unit,
    onFilterParamsChanged: (SearchParams) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        SearchContent(
            modifier = modifier.focusProperties { canFocus = filterState.showFilterOverlay.not() },
            filterState = filterState,
            onSearchQueryChange = onSearchQueryChange,
            onFilterClick = onFilterClick,
            pagingItems = pagingItems,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        if (filterState.showFilterOverlay) {
            BackHandler { onFilterDismiss() }

            FilterSelectionPopup(
                modifier = Modifier,
                searchParams = filterState.searchParams,
                onFilterParamsChanged = onFilterParamsChanged
            )
        }
    }
}

@Composable
@OptIn(ExperimentalTvMaterial3Api::class)
private fun SearchContent(
    modifier: Modifier,
    filterState: SearchViewModel.FilterState,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    pagingItems: LazyPagingItems<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchTextField(
                    modifier = Modifier.weight(1f),
                    searchQuery = filterState.searchParams.query,
                    onSearchQueryChange = onSearchQueryChange
                )

                Button(onClick = onFilterClick) {
                    Text("Filter")
                }
            }
        }

        filterState.appliedFilters?.let { appliedFilters ->
            item(span = { TvGridItemSpan(maxLineSpan) }) {
                TvLazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(appliedFilters) { filter ->
                        FilterChip(
                            content = {
                                Text("${filter.first}: ${filter.second}")
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove Filter"
                                )
                            },
                            selected = false,
                            onClick = {}
                        )
                    }
                }
            }
        }

        items(
            pagingItems.itemCount,
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
            onSearchQueryChange = setSearchQuery,
            filterState = SearchViewModel.FilterState(
                searchParams = SearchParams(
                    page = 1,
                    perPage = 20,
                    query = searchQuery,
                    isAdult = false
                )
            ),
            palettes = palettes,
            pagingItems = lazyPagingItems,
            onMediaClick = {},
            onFilterClick = {},
            onFilterDismiss = {},
            onFilterParamsChanged = {}
        )
    }
}