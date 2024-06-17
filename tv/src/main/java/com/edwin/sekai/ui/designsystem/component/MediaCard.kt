@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.MediaTitle
import com.edwin.sekai.ui.utils.formatMovieDuration
import com.edwin.sekai.ui.utils.getEpisodeInfo

// Constants
private const val CARD_MAX_HEIGHT = 234
private const val CARD_ASPECT_RATIO = 2f / 3f
private const val GRADIENT_START = 0.7f
private const val GRADIENT_ALPHA = 0.8f
private const val STAR_ICON_SIZE = 16
private const val ICON_SPACING = 4

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MediaCard(
    media: Media,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    val averageColor = remember {
        media.averageColorHex?.let { Color(android.graphics.Color.parseColor(it)) } ?: Color.Black
    }
    val closestPalette = remember(averageColor, palettes) {
        findClosestPalette(averageColor, palettes)
    }
    val textColor = remember(closestPalette) {
        closestPalette?.let {
            Color(android.graphics.Color.parseColor(it.onPrimary))
        } ?: if (averageColor.luminance() > 0.5f) Color.Black else Color.White
    }
    val gradientColors = remember(averageColor) {
        listOf(
            Color.Transparent,
            averageColor.copy(alpha = GRADIENT_ALPHA)
        )
    }

    Card(
        onClick = { onClick(media.id) },
        modifier = modifier
            .heightIn(max = CARD_MAX_HEIGHT.dp)
            .aspectRatio(CARD_ASPECT_RATIO)
    ) {
        Box {
            // Background Image
            PreviewAbleSubComposeImage(
                imageUrl = media.coverImage,
                previewImage = painterResource(id = R.drawable.naruto),
                contentDescription = "${media.title} cover image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = gradientColors,
                            startY = GRADIENT_START
                        )
                    )
            )

            // Media Information
            MediaInfo(
                media = media,
                textColor = textColor,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun MediaInfo(
    media: Media,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Episodes/Duration
        Text(
            text = when (media) {
                is Media.TvSeries -> getEpisodeInfo(media)
                is Media.Movie -> formatMovieDuration(media.duration)
            },
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )

        // Title
        MediaTitle(
            title = media.title,
            textColor = textColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Rating or Popularity
        Row(verticalAlignment = Alignment.CenterVertically) {
            media.averageScore?.let { averageScore ->
                ShowStarRating(averageScore, media.startDate, textColor)
            } ?: ShowPopularity(media.popularity, media.startDate, textColor)
        }
    }
}

@Composable
private fun ShowStarRating(averageScore: Int?, startDate: Int?, textColor: Color) {
    Icon(
        imageVector = Icons.Filled.Star,
        contentDescription = stringResource(R.string.star_rating_content_description),
        tint = textColor,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = listOfNotNull(
            averageScore?.toFloat()?.div(10),
            startDate
        ).joinToString(stringResource(R.string.dot_separator)),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.labelMedium,
        color = textColor
    )
}

@Composable
private fun ShowPopularity(popularity: Int?, startDate: Int?, textColor: Color) {
    Icon(
        imageVector = Icons.Filled.ThumbUp,
        contentDescription = stringResource(R.string.popularity_content_description),
        tint = textColor,
        modifier = Modifier.size(STAR_ICON_SIZE.dp)
    )

    Spacer(modifier = Modifier.width(ICON_SPACING.dp))

    Text(
        text = listOfNotNull(
            popularity,
            startDate
        ).joinToString(stringResource(R.string.dot_separator)),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.labelMedium,
        color = textColor
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
        MediaCard(media = media, palettes = palettes)
    }
}