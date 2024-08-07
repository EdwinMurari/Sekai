package com.edwin.sekai.ui.feature.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyListScope
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import com.edwin.data.model.Media
import com.edwin.data.model.MediaCollections
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.CarouselMediaList
import com.edwin.sekai.ui.designsystem.component.CategoryHeader
import com.edwin.sekai.ui.designsystem.component.FeaturedCarouselMediaList
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.previewprovider.MediaCollectionPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

// Constants
private const val IMMERSIVE_LIST_CONTENT_TYPE = "ImmersiveList"
private const val CAROUSEL_LIST_CONTENT_TYPE = "CarouselList"
private const val FEATURED_LIST_CONTENT_TYPE = "FeaturedList"

@Composable
fun BrowseRoute(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BrowseScreen(
        uiState = uiState,
        palettes = palettes,
        isTopBarVisible = isTopBarVisible,
        onMediaClick = onMediaClick,
        updateTopBarVisibility = updateTopBarVisibility,
        modifier = modifier,
        contentPaddingValues = contentPaddingValues
    )
}

@Composable
fun BrowseScreen(
    modifier: Modifier = Modifier,
    uiState: BrowseViewModel.BrowseScreenUiState,
    palettes: Map<String, Material3Palette>,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    onMediaClick: (Int) -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    when (uiState) {
        is BrowseViewModel.BrowseScreenUiState.Success -> {
            MediaCollections(
                modifier = modifier,
                contentPaddingValues = contentPaddingValues,
                collection = uiState.collection,
                palettes = palettes,
                isTopBarVisible = isTopBarVisible,
                onMediaClick = onMediaClick,
                updateTopBarVisibility = updateTopBarVisibility
            )
        }

        is BrowseViewModel.BrowseScreenUiState.Error -> {
            SomethingWentWrong(
                modifier = modifier,
                text = "Something went wrong: ${
                    uiState.errors.map { it.message }.joinToString(", ")
                }",
                buttonText = "Retry",
                onAction = {}
            )
        }

        BrowseViewModel.BrowseScreenUiState.Loading -> {
            Loading(modifier = modifier)
        }
    }
}

@Composable
fun MediaCollections(
    modifier: Modifier,
    collection: MediaCollections,
    palettes: Map<String, Material3Palette>,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    onMediaClick: (Int) -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    val lazyListState = rememberTvLazyListState()

    val shouldShowTopBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset < 300
        }
    }

    LaunchedEffect(shouldShowTopBar) {
        updateTopBarVisibility(shouldShowTopBar)
    }

    LaunchedEffect(isTopBarVisible) {
        if (isTopBarVisible) lazyListState.animateScrollToItem(0)
    }

    val layoutDirection = LocalLayoutDirection.current
    val itemContentPaddingValues = remember(layoutDirection) {
        PaddingValues(
            start = contentPaddingValues.calculateStartPadding(layoutDirection),
            end = contentPaddingValues.calculateEndPadding(layoutDirection)
        )
    }

    TvLazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        contentPadding = PaddingValues(
            top = contentPaddingValues.calculateTopPadding(),
            bottom = contentPaddingValues.calculateBottomPadding()
        )
    ) {
        featuredCarouselSection(
            modifier = Modifier.padding(itemContentPaddingValues),
            mediaList = collection.trendingTvSeries,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Trending Movies This Season",
            mediaList = collection.trendingMovies,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Popular Series This Season",
            mediaList = collection.popularTvSeries,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Top Series This Season",
            mediaList = collection.topTvSeries,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Trending Series All Time",
            mediaList = collection.trendingTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Popular Series All Time",
            mediaList = collection.popularTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Top Series All Time",
            mediaList = collection.topTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Popular Movies This Season",
            mediaList = collection.popularMovies,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Top Movies This Season",
            mediaList = collection.topMovies,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Trending Movies All Time",
            mediaList = collection.topMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Popular Movies All Time",
            mediaList = collection.popularMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )

        carouselSection(
            sectionHeader = "Top Movies All Time",
            mediaList = collection.topMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick,
            contentPaddingValues = itemContentPaddingValues
        )
    }
}

private fun TvLazyListScope.featuredCarouselSection(
    mediaList: List<Media>?,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    mediaList?.let {
        item(contentType = FEATURED_LIST_CONTENT_TYPE) {
            FeaturedCarouselMediaList(
                mediaList = mediaList,
                onMediaClick = onMediaClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }
    }
}

private fun TvLazyListScope.carouselSection(
    contentPaddingValues: PaddingValues,
    sectionHeader: String,
    mediaList: List<Media>?,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    mediaList?.let {
        item(contentType = CAROUSEL_LIST_CONTENT_TYPE, key = sectionHeader) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CategoryHeader(
                    text = sectionHeader,
                    modifier = Modifier.padding(contentPaddingValues)
                )
                CarouselMediaList(
                    contentPaddingValues = contentPaddingValues,
                    mediaList = mediaList,
                    palettes = palettes,
                    onMediaClick = onMediaClick,
                )
            }
        }
    }
}

@TvPreview
@Composable
fun PreviewBrowse(@PreviewParameter(MediaCollectionPreviewParameterProvider::class) mediaCollections: MediaCollections) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    SekaiTheme {
        BrowseScreen(
            uiState = BrowseViewModel.BrowseScreenUiState.Success(mediaCollections),
            isTopBarVisible = true,
            onMediaClick = {},
            updateTopBarVisibility = {},
            palettes = palettes,
            contentPaddingValues = PaddingValues(58.dp)
        )
    }
}