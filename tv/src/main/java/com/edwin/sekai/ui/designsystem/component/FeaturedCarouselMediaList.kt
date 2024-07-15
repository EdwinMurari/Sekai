package com.edwin.sekai.ui.designsystem.component

import android.content.res.Configuration
import android.graphics.Paint
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Carousel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import androidx.tv.material3.rememberCarouselState
import coil.compose.SubcomposeAsyncImage
import com.edwin.data.model.Media
import com.edwin.sekai.ui.designsystem.previewprovider.MediaListPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.GenreList
import com.edwin.sekai.ui.utils.MediaDescription
import com.edwin.sekai.ui.utils.MediaMetaDataDetailed
import com.edwin.sekai.ui.utils.MediaTitle

@OptIn(
    ExperimentalTvMaterial3Api::class
)
@Composable
fun FeaturedCarouselMediaList(
    modifier: Modifier = Modifier,
    mediaList: List<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit = {}
) {
    var carouselFocused by remember { mutableStateOf(false) }
    val carouselState = rememberCarouselState()
    val density = LocalDensity.current
    val shape = MaterialTheme.shapes.extraLarge
    val glowColor = MaterialTheme.colorScheme.secondaryContainer

    Carousel(
        itemCount = mediaList.size,
        carouselState = carouselState,
        modifier = modifier
            .border(
                width = 3.dp,
                color = if (carouselFocused) MaterialTheme.colorScheme.border else Color.Transparent,
                shape = shape,
            )
            .onFocusChanged { carouselFocused = it.isFocused }
            .handleDPadKeyEvents(onSelect = {
                onMediaClick(mediaList[carouselState.activeItemIndex].id)
            })
            .ifElse(
                condition = carouselFocused,
                ifTrueModifier = Modifier.drawBehind {
                    val canvasSize = size
                    val cornerRadius = shape.topEnd.toPx(
                        shapeSize = Size(canvasSize.width, canvasSize.height),
                        density = density
                    )
                    drawContext.canvas.nativeCanvas.apply {
                        drawRoundRect(
                            0f,
                            0f,
                            canvasSize.width,
                            canvasSize.height,
                            cornerRadius,
                            cornerRadius,
                            Paint().apply {
                                color = android.graphics.Color.TRANSPARENT
                                isAntiAlias = true
                                setShadowLayer(
                                    40.dp.toPx(),
                                    0f,
                                    0f,
                                    glowColor
                                        .copy(alpha = 0.60f)
                                        .toArgb()
                                )
                            }
                        )
                    }
                }
            )
            .clip(shape),
        contentTransformStartToEnd = (slideInHorizontally { it / 2 } + fadeIn())
            .togetherWith((slideOutHorizontally { -it / 2 }) + fadeOut()),
        contentTransformEndToStart = (slideInHorizontally { -it / 2 } + fadeIn())
            .togetherWith((slideOutHorizontally { it / 2 }) + fadeOut()),
    ) { index ->
        val movie = mediaList[index]

        CarouselItemBackground(
            media = movie,
            modifier = Modifier.fillMaxSize()
        )

        CarouselItemForeground(
            media = movie,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CarouselItemForeground(
    media: Media,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Bottom
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

@Composable
private fun CarouselItemBackground(media: Media, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        model = media.bannerImage ?: media.coverImage,
        contentDescription = "${media.title} banner image",
        contentScale = ContentScale.Crop,
        loading = { Loading() },
        modifier = modifier.drawWithContent {
            drawContent()
            drawRect(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.5f)
                    )
                )
            )
        }
    )
}

@Preview(
    device = "id:tv_1080p",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_TELEVISION
)
@Composable
fun FeaturedCarouselPreview(
    @PreviewParameter(MediaListPreviewParameterProvider::class) mediaList: List<Media>
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)
    SekaiTheme {
        FeaturedCarouselMediaList(
            mediaList = mediaList,
            palettes = palettes,
            modifier = Modifier.padding(50.dp)
        )
    }
}