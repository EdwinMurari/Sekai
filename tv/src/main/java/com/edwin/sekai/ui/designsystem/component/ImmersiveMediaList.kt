@file:OptIn(ExperimentalTvMaterial3Api::class, ExperimentalTvMaterial3Api::class)

package com.edwin.sekai.ui.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ImmersiveList
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import coil.compose.SubcomposeAsyncImage
import com.edwin.data.model.Media
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.previewprovider.MediaListPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.GenreList
import com.edwin.sekai.ui.utils.MediaDescription
import com.edwin.sekai.ui.utils.MediaMetaDataDetailed
import com.edwin.sekai.ui.utils.MediaTitle

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ImmersiveMediaList(
    mediaList: List<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    showTopBar: (Boolean) -> Unit
) {
    val immersiveListHeight = 426.dp
    val immersiveListWidth = 758.dp
    val cardHeight = 234.dp
    val gradientEndPadding = 40.dp
    val horizontalPadding = 58.dp

    val surfaceColor = MaterialTheme.colorScheme.surface
    val backgroundGradient = remember {
        createRadialGradientBrush(surfaceColor)
    }

    ImmersiveList(
        modifier = Modifier
            .height(immersiveListHeight + cardHeight / 4)
            .fillMaxWidth()
            .focusable(false),
        background = { index, _ ->
            AnimatedContent(
                targetState = mediaList[index],
                label = "bannerBackdrop"
            ) { media ->
                ImmersiveItemBackground(
                    media = media,
                    immersiveListHeight = immersiveListHeight,
                    immersiveListWidth = immersiveListWidth,
                    cardHeight = cardHeight,
                    gradientEndPadding = gradientEndPadding,
                    horizontalPadding = horizontalPadding,
                    backgroundGradient = backgroundGradient
                )
            }
        },
    ) {
        TvLazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .wrapContentHeight()
                .onFocusChanged {
                    if (it.hasFocus) {
                        showTopBar(false)
                    }
                }
                .onPreviewKeyEvent {
                    when {
                        Key.DirectionUp == it.key -> {
                            showTopBar(true)
                            true
                        }

                        else -> {
                            showTopBar(false)
                            false
                        }
                    }
                },
            contentPadding = PaddingValues(horizontal = horizontalPadding)
        ) {
            itemsIndexed(mediaList) { index, anime ->
                MediaCard(
                    media = anime,
                    modifier = Modifier.immersiveListItem(index),
                    palettes = palettes,
                    onClick = onMediaClick
                )
            }
        }
    }
}

@Composable
private fun ImmersiveItemBackground(
    media: Media,
    immersiveListHeight: Dp,
    immersiveListWidth: Dp,
    cardHeight: Dp,
    gradientEndPadding: Dp,
    horizontalPadding: Dp,
    backgroundGradient: ShaderBrush
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable(false)
    ) {
        SubcomposeAsyncImage(
            model = media.bannerImage,
            contentDescription = "${media.title} banner image",
            contentScale = ContentScale.Crop,
            loading = { Loading() },
            modifier = Modifier
                .height(immersiveListHeight)
                .width(immersiveListWidth)
                .align(Alignment.TopEnd)
                .focusable(false)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
        )
        ContentBlock(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = cardHeight + gradientEndPadding, start = horizontalPadding)
                .focusable(false),
            media = media
        )
    }
}


fun createRadialGradientBrush(backgroundColor: Color): ShaderBrush {
    return object : ShaderBrush() {
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
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ContentBlock(modifier: Modifier = Modifier, media: Media) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            GenreList(genres = media.genres)

            ProvideTextStyle(MaterialTheme.typography.headlineMedium) {
                MediaTitle(
                    title = media.title
                )
            }

            ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                MediaMetaDataDetailed(media)
            }

            MediaDescription(
                description = media.description,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }
    }
}

@TvPreview
@Composable
fun ImmersiveMediaListPreview(
    @PreviewParameter(MediaListPreviewParameterProvider::class) mediaList: List<Media>
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)
    SekaiTheme {
        ImmersiveMediaList(
            mediaList = mediaList,
            palettes = palettes,
            onMediaClick = {},
            showTopBar = {})
    }
}