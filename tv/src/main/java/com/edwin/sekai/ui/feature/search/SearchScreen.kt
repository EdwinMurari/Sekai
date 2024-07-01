package com.edwin.sekai.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.FilterChip
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.MediaCard
import com.edwin.sekai.ui.designsystem.component.MediaCardPlaceholder
import com.edwin.sekai.ui.designsystem.component.SearchTextField
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.previewprovider.MediaListPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchRoute(
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.uiState.collectAsLazyPagingItems()

    SearchScreen(
        searchQuery = searchQuery,
        pagingItems = lazyPagingItems,
        palettes = palettes,
        onMediaClick = onMediaClick,
        onSearchQueryChange = viewModel::onQueryChange,
        modifier = modifier
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    pagingItems: LazyPagingItems<Media>,
    palettes: Map<String, Material3Palette>,
    onSearchQueryChange: (String) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TvLazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(56.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = TvGridCells.Adaptive(minSize = 156.dp)
    ) {
        item(span = { TvGridItemSpan(maxLineSpan) }) {
            SearchTextField(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )
        }

        item(span = { TvGridItemSpan(maxLineSpan) }) {
            TvLazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(20) {
                    FilterChip(
                        content = { Text("Hello") },
                        selected = false,
                        onClick = {}
                    )
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
            searchQuery = searchQuery,
            onSearchQueryChange = setSearchQuery,
            palettes = palettes,
            pagingItems = lazyPagingItems,
            onMediaClick = {}
        )
    }
}