package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.DotSeparatedRow
import com.edwin.sekai.ui.designsystem.component.PreviewAbleSubComposeImage
import com.edwin.sekai.ui.designsystem.component.getAnnotatedString
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.formatMovieDuration
import com.edwin.sekai.ui.utils.getEpisodeInfo
import java.util.Locale

// Constants
private const val STAR_ICON_SIZE = 16
private const val ICON_SPACING = 4

@Composable
fun MediaDetailsSection(
    media: Media,
    onClickWatch: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val episodeNumber = 20 // TODO :: Get the last watched episode number

    Column(
        modifier = modifier
    ) {
        Row {
            PreviewAbleSubComposeImage(
                imageUrl = media.coverImage,
                contentDescription = "${media.title} cover image",
                contentScale = ContentScale.Crop,
                previewImage = painterResource(id = R.drawable.naruto),
                modifier = Modifier
                    .height(358.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 50.dp)
                    .fillMaxWidth()
            ) {
                GenreList(
                    genres = media.genres
                )

                MediaTitles(
                    mediaTitle = media.title
                )

                // Rating or Popularity
                DotSeparatedRow(
                    contents = arrayOf(
                        { Popularity(media.popularity) },
                        { StarRating(media.averageScore) },
                        { MediaContentInfo(media) },
                        { StartYear(media.startDate) }
                    )
                )

                MediaDescription(media.description)

                WatchButton(
                    episodeNumber = episodeNumber,
                    onClickWatch = { onClickWatch(media.id, it) }
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ColumnScope.MediaTitles(mediaTitle: String?, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = mediaTitle ?: stringResource(R.string.title_missing),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

    Text(
        text = "Romaji Name",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun GenreList(
    genres: List<String>?,
    modifier: Modifier = Modifier
) {
    genres?.let {
        Text(
            modifier = modifier,
            text = genres.joinToString(stringResource(R.string.dot_separator)) { it.uppercase(Locale.ROOT) },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun StarRating(averageScore: Int?) {
    if (averageScore == null) return

    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = stringResource(R.string.star_rating_content_description),
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = averageScore.toFloat().div(10).toString(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun Popularity(popularity: Int?) {
    if (popularity == null) return

    Icon(
        imageVector = Icons.Filled.ThumbUp,
        contentDescription = stringResource(R.string.popularity_content_description),
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = popularity.toString(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaContentInfo(media: Media) {
    Text(
        text = when (media) {
            is Media.TvSeries -> getEpisodeInfo(media)
            is Media.Movie -> formatMovieDuration(media.duration)
        },
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun StartYear(startDate: Int?) {
    if (startDate == null) return

    Text(
        text = startDate.toString(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun MediaDescription(
    description: String?,
    modifier: Modifier = Modifier
) {
    description?.let {
        Text(
            modifier = modifier,
            text = getAnnotatedString(htmlString = it),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 5
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun WatchButton(
    modifier: Modifier = Modifier,
    episodeNumber: Int,
    onClickWatch: (Int) -> Unit
) {
    Button(
        onClick = { onClickWatch(episodeNumber) },
        modifier = modifier.padding(top = 24.dp),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        Icon(
            imageVector = Icons.Rounded.PlayArrow,
            contentDescription = null
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = "Watch now",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@TvPreview
@Composable
fun PreviewMediaDetails(
    @PreviewParameter(MediaPreviewParameterProvider::class) media: Media
) {
    SekaiTheme {
        MediaDetailsSection(media = media, onClickWatch = { _, _ -> })
    }
}