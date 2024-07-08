package com.edwin.sekai.ui.feature.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyListScope
import com.edwin.data.model.Media
import com.edwin.data.model.MediaCollections
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.CarouselMediaList
import com.edwin.sekai.ui.designsystem.component.CategoryHeader
import com.edwin.sekai.ui.designsystem.component.ImmersiveMediaList
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.previewprovider.MediaCollectionPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

// Constants
private const val IMMERSIVE_LIST_CONTENT_TYPE = "ImmersiveList"
private const val CAROUSEL_LIST_CONTENT_TYPE = "CarouselList"

@Composable
fun BrowseRoute(
    onMediaClick: (Int) -> Unit,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BrowseScreen(
        uiState = uiState,
        palettes = palettes,
        onMediaClick = onMediaClick,
        modifier = modifier
    )
}

@Composable
fun BrowseScreen(
    uiState: BrowseViewModel.BrowseScreenUiState,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is BrowseViewModel.BrowseScreenUiState.Success -> {
            MediaCollections(
                modifier = modifier,
                collection = uiState.collection,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }

        BrowseViewModel.BrowseScreenUiState.Error -> {
            SomethingWentWrong(
                modifier = modifier,
                text = "Something went wrong",
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
    onMediaClick: (Int) -> Unit
) {
    TvLazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(40.dp),
        contentPadding = PaddingValues(bottom = 56.dp)
    ) {
        immersiveListSection(
            mediaList = collection.trendingTvSeries,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Trending Movies This Season",
            mediaList = collection.trendingMovies,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Popular Series This Season",
            mediaList = collection.popularTvSeries,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Top Series This Season",
            mediaList = collection.topTvSeries,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Trending Series All Time",
            mediaList = collection.trendingTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Popular Series All Time",
            mediaList = collection.popularTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Top Series All Time",
            mediaList = collection.topTvSeriesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Popular Movies This Season",
            mediaList = collection.popularMovies,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Top Movies This Season",
            mediaList = collection.topMovies,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Trending Movies All Time",
            mediaList = collection.topMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Popular Movies All Time",
            mediaList = collection.popularMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        carouselSection(
            sectionHeader = "Top Movies All Time",
            mediaList = collection.topMoviesAllTime,
            palettes = palettes,
            onMediaClick = onMediaClick
        )
    }
}

private fun TvLazyListScope.immersiveListSection(
    mediaList: List<Media.TvSeries>?,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    mediaList?.let {
        item(
            contentType = IMMERSIVE_LIST_CONTENT_TYPE
        ) {
            ImmersiveMediaList(
                mediaList = it,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}

private fun TvLazyListScope.carouselSection(
    sectionHeader: String,
    mediaList: List<Media>?,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    mediaList?.let {
        item(contentType = CAROUSEL_LIST_CONTENT_TYPE, key = sectionHeader) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CategoryHeader(text = sectionHeader)
                CarouselMediaList(
                    mediaList = mediaList,
                    palettes = palettes,
                    onMediaClick = onMediaClick
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
            onMediaClick = {},
            palettes = palettes
        )
    }
}