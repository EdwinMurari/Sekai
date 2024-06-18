package com.edwin.sekai.ui.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.GradientBackdrop
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.previewprovider.MediaDetailsPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.details.components.MediaDetailsSection
import com.edwin.sekai.ui.feature.details.components.episodesSection
import com.edwin.sekai.ui.feature.details.components.recommendationsSection
import com.edwin.sekai.ui.feature.details.components.relationsSection

@Composable
fun DetailsRoute(
    palettes: Map<String, Material3Palette>,
    onClickWatch: (Int, Int) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreen(
        uiState = uiState,
        palettes = palettes,
        onClickWatch = onClickWatch,
        onMediaClick = onMediaClick,
        modifier = modifier
    )
}

@Composable
fun DetailsScreen(
    uiState: DetailsViewModel.DetailsUiState,
    palettes: Map<String, Material3Palette>,
    onClickWatch: (Int, Int) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        DetailsViewModel.DetailsUiState.Error -> {
            SomethingWentWrong(
                modifier = modifier,
                text = "Something went wrong",
                buttonText = "Retry",
                onAction = {}
            )
        }

        DetailsViewModel.DetailsUiState.Loading -> {
            Loading(modifier = modifier)
        }

        is DetailsViewModel.DetailsUiState.Success -> {
            Content(
                mediaDetails = uiState.mediaDetails,
                palettes = palettes,
                onClickWatch = onClickWatch,
                onMediaClick = onMediaClick,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Content(
    mediaDetails: MediaDetails,
    palettes: Map<String, Material3Palette>,
    onClickWatch: (Int, Int) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        GradientBackdrop(
            imageUrl = mediaDetails.media.bannerImage
        )

        TvLazyColumn(
            contentPadding = PaddingValues(
                top = 100.dp,
                bottom = 58.dp,
                start = 48.dp,
                end = 48.dp
            ),
            modifier = modifier,
        ) {
            item {
                MediaDetailsSection(
                    media = mediaDetails.media,
                    onClickWatch = onClickWatch
                )
            }

            episodesSection(
                mediaDetails = mediaDetails,
                onClickWatch = { onClickWatch(mediaDetails.media.id, it) }
            )

            relationsSection(
                mediaDetails = mediaDetails,
                palettes = palettes,
                onMediaClick = onMediaClick
            )

            recommendationsSection(
                mediaDetails = mediaDetails,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}

@TvPreview
@Composable
fun PreviewDetailsScreen(
    @PreviewParameter(MediaDetailsPreviewParameterProvider::class) mediaDetails: MediaDetails
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    SekaiTheme {
        DetailsScreen(
            uiState = DetailsViewModel.DetailsUiState.Success(mediaDetails),
            onClickWatch = { _, _ -> },
            onMediaClick = {},
            palettes = palettes
        )
    }
}