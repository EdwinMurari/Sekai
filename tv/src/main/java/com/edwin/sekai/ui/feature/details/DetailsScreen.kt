package com.edwin.sekai.ui.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.edwin.data.model.Media
import com.edwin.sekai.ui.designsystem.component.GradientBackdrop
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.previewprovider.PreviewParameterData
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.details.components.MediaDetailsSection

@Composable
fun DetailsRoute(onClickWatch: (Int, Int) -> Unit) {
    DetailsScreen(
        media = PreviewParameterData.tvSeriesList.first(), // TODO :: Get the real data
        onClickWatch = onClickWatch
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DetailsScreen(
    media: Media,
    onClickWatch: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        GradientBackdrop(
            imageUrl = media.bannerImage
        )

        Content(
            media = media,
            onClickWatch = onClickWatch
        )
    }
}

@Composable
fun Content(
    media: Media,
    onClickWatch: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
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
                media = media,
                onClickWatch = onClickWatch
            )
        }

        /*item {
            CastAndCrewList(
                castAndCrew = movieDetails.castAndCrew
            )
        }

        item {
            MoviesRow(
                title = StringConstants
                    .Composable
                    .movieDetailsScreenSimilarTo(movieDetails.name),
                titleStyle = MaterialTheme.typography.titleMedium,
                movies = movieDetails.similarMovies,
                onMovieClick = refreshScreenWithNewMovie
            )
        }

        item {
            MovieReviews(
                modifier = Modifier.padding(top = childPadding.top),
                reviewsAndRatings = movieDetails.reviewsAndRatings
            )
        }

        item {
            Box(
                modifier = Modifier
                    .padding(horizontal = childPadding.start)
                    .padding(BottomDividerPadding)
                    .fillMaxWidth()
                    .height(1.dp)
                    .alpha(0.15f)
                    .background(MaterialTheme.colorScheme.onSurface)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = childPadding.start),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val itemModifier = Modifier.width(192.dp)

                TitleValueText(
                    modifier = itemModifier,
                    title = stringResource(R.string.status),
                    value = movieDetails.status
                )
                TitleValueText(
                    modifier = itemModifier,
                    title = stringResource(R.string.original_language),
                    value = movieDetails.originalLanguage
                )
                TitleValueText(
                    modifier = itemModifier,
                    title = stringResource(R.string.budget),
                    value = movieDetails.budget
                )
                TitleValueText(
                    modifier = itemModifier,
                    title = stringResource(R.string.revenue),
                    value = movieDetails.revenue
                )
            }
        }*/
    }
}

@Preview
@Composable
fun PreviewDetailsScreen(
    @PreviewParameter(MediaPreviewParameterProvider::class) media: Media
) {
    SekaiTheme {
        DetailsScreen(media, onClickWatch = { _, _ -> })
    }
}