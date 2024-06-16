@file:OptIn(ExperimentalTvMaterial3Api::class, ExperimentalTvMaterial3Api::class)

package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ImmersiveList
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.SubcomposeAsyncImage
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

// TODO :: Cleanup required
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ImmersiveMediaList(
    mediaList: List<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    val immersiveListHeight = 426.dp
    val immersiveListWidth = 758.dp
    val cardHeight = 234.dp

    if (mediaList.isNotEmpty()) {
        ImmersiveList(
            modifier = Modifier
                .height(immersiveListHeight + cardHeight / 4)
                .fillMaxWidth(),
            background = { index, _ ->
                val media = mediaList[index]
                AnimatedContent(targetState = index) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val backgroundColor = MaterialTheme.colorScheme.surface
                        val gradient = object : ShaderBrush() {
                            override fun createShader(size: Size): Shader {
                                val biggerDimension = maxOf(size.height, size.width)
                                return RadialGradientShader(
                                    colors = listOf(Color.Transparent, backgroundColor),
                                    center = Offset(size.width * 0.75f, 0f),
                                    radius = biggerDimension,
                                    colorStops = listOf(0f, 0.5f)
                                )
                            }
                        }

                        SubcomposeAsyncImage(
                            model = media.bannerImage,
                            contentDescription = "${media.title} banner",
                            contentScale = ContentScale.Crop,
                            loading = { Loading() },
                            modifier = Modifier
                                .height(immersiveListHeight)
                                .width(immersiveListWidth)
                                .align(Alignment.TopEnd)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(gradient)
                        )
                        ContentBlock(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(bottom = cardHeight + 40.dp, start = 58.dp),
                            media = media
                        )
                    }
                }
            },
        ) {
            TvLazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.wrapContentHeight(),
                contentPadding = PaddingValues(horizontal = 58.dp)
            ) {
                itemsIndexed(mediaList) { index, anime ->
                    var isFocused by remember { mutableStateOf(false) }
                    MediaCard(
                        media = anime,
                        modifier = Modifier
                            .onFocusChanged { isFocused = it.isFocused }
                            .immersiveListItem(index),
                        palettes = palettes,
                        onClick = onMediaClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ContentBlock(modifier: Modifier = Modifier, media: Media) {
    Column(modifier = modifier) {
        LabelText(
            labels = listOfNotNull(
//                media.genres.joinToString(separator = "/"),
                when (media) {
                    is Media.TvSeries -> getEpisodeInfo(media)
                    is Media.Movie -> formatMovieDuration(media.duration)
                },
                media.startDate.toString()
            )
        )
        Text(
            text = media.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = getAnnotatedString(htmlString = media.description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = 12.dp)
        )
    }
}

@Composable
private fun getEpisodeInfo(media: Media.TvSeries): String {
    return if (media.episodes > 0) {
        media.nextAiringEpisode?.episode?.let { nextAiringEpisode ->
            stringResource(id = R.string.episodes_format, nextAiringEpisode, media.episodes)
        } ?: stringResource(id = R.string.episodes, media.episodes)
    } else {
        media.nextAiringEpisode?.episode?.let { nextAiringEpisode ->
            stringResource(id = R.string.episodes_ongoing, nextAiringEpisode)
        } ?: stringResource(id = R.string.ongoing)
    }
}

@Composable
private fun formatMovieDuration(durationMinutes: Int): String {
    val hours = durationMinutes / 60
    val minutes = durationMinutes % 60

    return if (hours > 0) {
        stringResource(id = R.string.hours_minutes, hours, minutes)
    } else {
        stringResource(id = R.string.minutes, minutes)
    }
}

@Composable
fun LabelText(labels: List<String>) {
    Text(
        text = labels.joinToString(getDotSeparator()),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

fun getDotSeparator() = " • "

fun getAnnotatedString(htmlString: String): AnnotatedString {
    return buildAnnotatedString {
        append(HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }
}

// Reuse your existing Media objects from MediaPreviewParameterProvider
val sampleMediaList = MediaPreviewParameterProvider().values.toList()

// New PreviewParameterProvider for List<Media>
class MediaListPreviewParameterProvider : PreviewParameterProvider<List<Media>> {
    override val values = sequenceOf(sampleMediaList) // Provide your media list
}

// Your ImmersiveMedia ListPreview
@Preview(device = "id:tv_1080p")
@Composable
fun ImmersiveMediaListPreview(
    @PreviewParameter(MediaListPreviewParameterProvider::class) mediaList: List<Media>
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)
    SekaiTheme {
        ImmersiveMediaList(mediaList = mediaList, palettes = palettes, onMediaClick = {})
    }
}