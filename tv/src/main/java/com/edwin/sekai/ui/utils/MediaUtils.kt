package com.edwin.sekai.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.LocalTextStyle
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.DotSeparatedRow
import java.util.Locale

// Constants
private const val STAR_ICON_SIZE = 16
private const val ICON_SPACING = 4

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaTitle(
    modifier: Modifier = Modifier,
    title: String?
) {
    Text(
        modifier = modifier,
        text = title ?: stringResource(R.string.title_missing),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = LocalTextStyle.current,
        color = LocalContentColor.current
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaTitles(mainTitle: String?, mediaTitle: MediaDetails.Title?) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ProvideTextStyle(MaterialTheme.typography.headlineMedium) {
            MediaTitle(title = mainTitle)
        }

        Text(
            text = mediaTitle?.english?.let { mediaTitle.romaji } // If english title is not present then main title would be romaji so use native here instead
                ?: mediaTitle?.native
                ?: stringResource(R.string.title_missing),
            style = MaterialTheme.typography.titleMedium,
            color = LocalContentColor.current
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaDescription(description: String?, maxLines: Int, modifier: Modifier = Modifier) {
    if (description == null) return
    Text(
        modifier = modifier,
        text = getAnnotatedString(htmlString = description),
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GenreList(
    genres: List<String>?,
    modifier: Modifier = Modifier
) {
    genres?.let {
        Text(
            modifier = modifier,
            text = genres.joinToString(stringResource(R.string.slash_separator)) {
                it.uppercase(
                    Locale.ROOT
                )
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = LocalContentColor.current
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaContentInfo(media: Media) {
    Text(
        text = when (media) {
            is Media.TvSeries -> getEpisodeInfo(media)
            is Media.Movie -> formatMovieDuration(media.duration)
        },
        style = LocalTextStyle.current,
        color = LocalContentColor.current
    )
}

@Composable
fun MediaMetaData(media: Media) {
    val contentList = mutableListOf<@Composable () -> Unit>().apply {
        add {
            media.averageScoreOutOfTen?.let { StarRating(it) }
                ?: media.popularity?.let { Popularity(it) }
        }
        add { StartYear(media.startDate) }
    }

    DotSeparatedRow(
        contents = contentList.toTypedArray()
    )
}

@Composable
fun MediaMetaDataDetailed(media: Media, modifier: Modifier = Modifier) {
    val contentList = mutableListOf<@Composable () -> Unit>().apply {
        media.popularity?.let { add { Popularity(it) } }
        media.averageScoreOutOfTen?.let { add { StarRating(it) } }
        add { MediaContentInfo(media) }
        add { StartYear(media.startDate) }
    }

    DotSeparatedRow(
        modifier = modifier,
        contents = contentList.toTypedArray()
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun StarRating(averageScore: Float) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = stringResource(R.string.star_rating_content_description),
        tint = LocalContentColor.current,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = averageScore.toString(),
        color = LocalContentColor.current
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun Popularity(popularity: Int) {
    Icon(
        imageVector = Icons.Filled.ThumbUp,
        contentDescription = stringResource(R.string.popularity_content_description),
        tint = LocalContentColor.current,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = popularity.toString(),
        color = LocalContentColor.current
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun StartYear(startDate: Int?) {
    if (startDate == null) return

    Text(
        text = startDate.toString(),
        color = LocalContentColor.current
    )
}

@Composable
private fun getEpisodeInfo(media: Media.TvSeries): String {
    return when {
        media.episodesCount != null && media.episodesCount!! > 0 -> {
            media.nextAiringEpisode?.episode?.let { nextAiringEpisode ->
                stringResource(
                    id = R.string.episodes_format,
                    nextAiringEpisode,
                    media.episodesCount!!
                )
            } ?: stringResource(id = R.string.episodes, media.episodesCount!!)
        }

        media.nextAiringEpisode?.episode != null -> {
            stringResource(id = R.string.episodes_ongoing, media.nextAiringEpisode!!.episode)
        }

        else -> stringResource(id = R.string.ongoing)
    }
}

@Composable
private fun formatMovieDuration(durationMinutes: Int?): String {
    return durationMinutes?.let {
        val hours = it / 60
        val minutes = it % 60
        if (hours > 0) stringResource(id = R.string.hours_minutes, hours, minutes)
        else stringResource(id = R.string.minutes, minutes)
    } ?: ""
}

private fun getAnnotatedString(htmlString: String): AnnotatedString {
    return buildAnnotatedString {
        append(HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }
}