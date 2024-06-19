@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.MediaContentInfo
import com.edwin.sekai.ui.utils.MediaMetaData
import com.edwin.sekai.ui.utils.MediaTitle

// Constants
private const val CARD_HEIGHT = 234
private const val CARD_ASPECT_RATIO = 2f / 3f
private const val GRADIENT_START = 1f

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaCard(
    media: Media,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier,
    relationType: String? = null,
    onClick: (Int) -> Unit = {}
) {
    val averageColor = remember {
        media.averageColorHex?.let { Color(android.graphics.Color.parseColor(it)) } ?: Color.Black
    }
    val closestPalette = remember(averageColor, palettes) {
        findClosestPalette(averageColor, palettes)
    }
    val contentColor = remember(closestPalette) {
        closestPalette?.let {
            Color(android.graphics.Color.parseColor(it.onPrimary))
        } ?: if (averageColor.luminance() > 0.5f) Color.Black else Color.White
    }
    val primaryColor = remember(closestPalette) {
        (closestPalette?.primary?.let { Color(android.graphics.Color.parseColor(it)) }
            ?: averageColor)
    }
    val gradientColors = remember(averageColor) {
        arrayOf(
            0.5f to Color.Transparent,
            GRADIENT_START to primaryColor
        )
    }

    Card(
        onClick = { onClick(media.id) },
        modifier = modifier
            .heightIn(max = CARD_HEIGHT.dp)
            .aspectRatio(CARD_ASPECT_RATIO)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Box {
                MediaCoverImage(
                    imageUrl = media.coverImage,
                    contentDescription = "${media.title} cover image"
                )

                GradientOverlay(gradientColors)

                MediaInfo(
                    media = media,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )

                RelationTypeTag(
                    relationType = relationType,
                    backgroundColor = primaryColor,
                    textColor = contentColor,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun GradientOverlay(gradientColors: Array<Pair<Float, Color>>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = gradientColors))
    )
}

@Composable
private fun MediaCoverImage(imageUrl: String?, contentDescription: String) {
    PreviewAbleSubComposeImage(
        imageUrl = imageUrl,
        previewImage = painterResource(id = R.drawable.naruto),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxSize()
    )
}

@Composable
private fun MediaInfo(
    media: Media,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ProvideTextStyle(MaterialTheme.typography.labelSmall) { MediaContentInfo(media) }

        ProvideTextStyle(MaterialTheme.typography.titleMedium) { MediaTitle(title = media.title) }

        Spacer(modifier = Modifier.height(4.dp))

        ProvideTextStyle(MaterialTheme.typography.labelMedium) { MediaMetaData(media) }
    }
}

@Composable
private fun RelationTypeTag(
    relationType: String?,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier
) {
    if (relationType == null) return

    Text(
        text = relationType,
        color = textColor,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
            .clip(RoundedCornerShape(CornerSize(4.dp)))
            .background(backgroundColor)
            .padding(2.dp)
    )
}

@TvPreview
@Composable
fun MediaCardPreview(
    @PreviewParameter(MediaPreviewParameterProvider::class) media: Media
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    SekaiTheme {
        MediaCard(
            media = media,
            palettes = palettes
        )
    }
}