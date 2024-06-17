package com.edwin.sekai.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.R

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaTitle(
    modifier: Modifier = Modifier,
    title: String?,
    textColor: Color
) {
    Text(
        modifier = modifier,
        text = title ?: stringResource(R.string.title_missing),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium,
        color = textColor
    )
}

@Composable
fun getEpisodeInfo(media: Media.TvSeries): String {
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
fun formatMovieDuration(durationMinutes: Int?): String {
    return durationMinutes?.let {
        val hours = it / 60
        val minutes = it % 60
        if (hours > 0) stringResource(id = R.string.hours_minutes, hours, minutes)
        else stringResource(id = R.string.minutes, minutes)
    } ?: ""
}