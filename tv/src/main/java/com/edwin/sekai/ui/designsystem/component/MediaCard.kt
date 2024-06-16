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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardColors
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import androidx.tv.material3.contentColorFor
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.previewprovider.MediaPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

@Composable
fun MediaCard(
    media: Media,
    modifier: Modifier = Modifier,
    onClick: (Media) -> Unit = {}
) {
    Card(
        onClick = { onClick(media) },
        modifier = modifier
            .heightIn(max = 280.dp)
            .aspectRatio(2f / 3f),
        colors = mediaCardColors()
    ) {
        Box {
            PreviewAbleSubComposeImage(
                imageUrl = media.coverImage,
                previewImage = painterResource(id = R.drawable.naruto),
                contentDescription = "${media.title} coverImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize() // Image fills the entire card
            )

            // Gradient overlay at the bottom
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(android.graphics.Color.parseColor(media.averageColorHex)) // Use average color
                            ),
                            startY = 0.7f // Adjust gradient start position
                        )
                    )
            )

            // Media information on top of the gradient
            MediaInfo(
                media = media,
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Get the appropriate text color based on the background
        val textColor =
            getTextColorForBackground(Color(android.graphics.Color.parseColor(media.averageColorHex)))

        Text(
            text = when (media) {
                is Media.TvSeries -> {
                    if (media.episodes > 0) {
                        // If total episodes are known
                        "${media.nextAiringEpisode?.episode ?: media.episodes} / ${media.episodes} Episodes"
                    } else {
                        // If total episodes are not yet known (ongoing series)
                        "${media.nextAiringEpisode?.episode ?: "?"} Episodes (Ongoing)"
                    }
                }

                is Media.Movie -> "${media.duration} Minutes"
            },
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )

        Text(
            text = media.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium, // Use a larger title style
            color = textColor
        )

        Spacer(modifier = Modifier.height(4.dp)) // Smaller space

        Text(
            text = "${media.averageScore}/10 • ${media.startDate}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}

/**
 * Determines the appropriate text color (onPrimary or onSurface) based on the
 * provided background color, using Material 3's color system.
 */
@Composable
fun getTextColorForBackground(backgroundColor: Color): Color {
    val colorScheme = MaterialTheme.colorScheme
    return if (backgroundColor.luminance() > 0.5f) {
        // Light background, use 'onSurface' for contrast
        colorScheme.onSurface
    } else {
        // Dark background, use 'onPrimary' for contrast
        colorScheme.onPrimary
    }
}

@Composable
fun mediaCardColors(): CardColors {
    val containerColor = MaterialTheme.colorScheme.tertiaryContainer
    val focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
    val pressedContainerColor = MaterialTheme.colorScheme.tertiaryContainer

    return CardDefaults.colors(
        containerColor = containerColor,
        contentColor = contentColorFor(containerColor),
        focusedContainerColor = containerColor,
        focusedContentColor = contentColorFor(focusedContainerColor),
        pressedContainerColor = focusedContainerColor,
        pressedContentColor = contentColorFor(pressedContainerColor)
    )
}

@TvPreview
@Composable
fun MediaCardPreview(
    @PreviewParameter(MediaPreviewParameterProvider::class) media: Media
) {
    SekaiTheme {
        MediaCard(media = media)
    }
}