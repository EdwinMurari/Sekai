package com.edwin.sekai.ui.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import androidx.tv.material3.Surface
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
private const val CARD_WIDTH = 156

@Composable
fun MediaCard(
    media: Media,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier,
    relationType: String? = null,
    onClick: () -> Unit = {}
) {
    val averageColor = remember(media) {
        parseColor(media.averageColorHex) ?: Color.Black
    }
    val closestPalette = remember(averageColor, palettes) {
        findClosestPalette(averageColor, palettes)
    }
    val contentColor = remember(closestPalette) {
        parseColor(closestPalette?.onPrimary)
            ?: if (averageColor.luminance() > 0.5f) Color.Black else Color.White
    }
    val primaryColor = remember(closestPalette) {
        parseColor(closestPalette?.primary) ?: averageColor
    }

    val interactionSource = remember { MutableInteractionSource() }

    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) MaterialTheme.colorScheme.border
        else Color.Transparent,
        label = "Media Card Border Color"
    )

    Surface(
        modifier = modifier,
        interactionSource = interactionSource,
        shape = ClickableSurfaceDefaults.shape(
            shape = RoundedCornerShape(0.dp),
            focusedShape = RoundedCornerShape(0.dp),
            pressedShape = RoundedCornerShape(0.dp),
        ),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = Color.Transparent,
            focusedContentColor = MaterialTheme.colorScheme.onSurface,
            pressedContainerColor = Color.Transparent,
            pressedContentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = onClick,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.width(IntrinsicSize.Min)
            ) {
                CompositionLocalProvider(LocalContentColor provides contentColor) {
                    Box(
                        modifier = Modifier
                            .border(
                                color = borderColor,
                                shape = MaterialTheme.shapes.large,
                                width = 3.dp
                            )
                            .size(width = CARD_WIDTH.dp, height = CARD_HEIGHT.dp)
                            .clip(MaterialTheme.shapes.large)
                    ) {
                        MediaCoverImage(
                            imageUrl = media.coverImage,
                            contentDescription = "${media.title} cover image"
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

                MediaInfo(
                    media = media,
                    modifier = Modifier
                )
            }
        }
    )
}

@Composable
fun MediaCardPlaceholder(
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {},
        modifier = modifier.size(width = CARD_WIDTH.dp, height = CARD_HEIGHT.dp)
    ) {
        Box(modifier = Modifier.background(color = Color.Green))
    }
}

@Composable
private fun MediaCoverImage(imageUrl: String?, contentDescription: String) {
    PreviewAbleSubComposeImage(
        imageUrl = imageUrl,
        previewImage = painterResource(id = R.drawable.naruto),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
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

fun parseColor(colorString: String?): Color? {
    return colorString?.let { Color(android.graphics.Color.parseColor(it)) }
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