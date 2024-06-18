package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyHorizontalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.list.TvLazyListScope
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import androidx.tv.material3.WideClassicCard
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.PreviewAbleSubComposeImage
import java.util.Locale

fun TvLazyListScope.episodesSection(mediaDetails: MediaDetails, onClickWatch: (Int) -> Unit) {
    if (mediaDetails is MediaDetails.TvSeries && !mediaDetails.episodes.isNullOrEmpty()) {
        sectionHeader("Episodes")
        item {
            EpisodesSection(mediaDetails.episodes!!, onEpisodeSelected = onClickWatch)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun EpisodesSection(
    episodesList: List<MediaDetails.Episode>,
    onEpisodeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (episodesList.size > 50) {
        Column(modifier = modifier) {
            val chunkSize = 26
            val chunkedEpisodes = episodesList.chunked(chunkSize)
            var selectedTabIndex by remember { mutableIntStateOf(0) }
            TabRow(
                modifier = Modifier.padding(horizontal = 58.dp, vertical = 12.dp),
                selectedTabIndex = selectedTabIndex
            ) {
                chunkedEpisodes.forEachIndexed { index, _ ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onFocus = {
                            selectedTabIndex = index
                        },
                    ) {
                        Text(
                            "${(index * chunkSize) + 1} - ${(index + 1) * chunkSize}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            EpisodeListGrid(
                episodesList = chunkedEpisodes[selectedTabIndex].toList(),
                onEpisodeSelected = onEpisodeSelected
            )
        }
    } else {
        EpisodeListGrid(
            episodesList = episodesList,
            onEpisodeSelected = onEpisodeSelected
        )
    }
}

@Composable
fun EpisodeListGrid(episodesList: List<MediaDetails.Episode>, onEpisodeSelected: (Int) -> Unit) {
    TvLazyHorizontalGrid(
        rows = TvGridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 58.dp, vertical = 12.dp),
        modifier = Modifier.height((83 * 2 + 8).dp)
    ) {
        items(episodesList, key = { it.number }) { episode ->
            EpisodeCard(
                episode = episode,
                onEpisodeSelected = onEpisodeSelected
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun EpisodeCard(episode: MediaDetails.Episode, onEpisodeSelected: (Int) -> Unit) {
    WideClassicCard(
        onClick = { onEpisodeSelected(episode.number) },
        image = {
            PreviewAbleSubComposeImage(
                previewImage = painterResource(id = R.drawable.naruto_ep1),
                imageUrl = episode.thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = "${episode.number} thumbnail",
                modifier = Modifier
                    .width(147.dp)
                    .aspectRatio(16f / 9f)
            )

            Text(
                text = "${episode.number}.",
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(horizontal = 8.dp)
            )
        },
        title = {
            Text(
                text = episode.title ?: "Episode ${episode.number}",
                maxLines = episode.duration?.let { 1 } ?: 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .width(138.dp)
            )
        },
        subtitle = {
            if (episode.filler) {
                Text(
                    text = "Filler".uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onTertiaryContainer),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .width(138.dp)
                )
            }
        },
        description = {
        }
    )
}