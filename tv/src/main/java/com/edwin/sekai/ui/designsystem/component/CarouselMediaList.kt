package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import com.edwin.data.model.Media
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.previewprovider.MediaListPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

@Composable
fun CarouselMediaList(
    modifier: Modifier = Modifier,
    mediaList: List<Media>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit = {}
) {
    TvLazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(horizontal = 58.dp)
    ) {
        items(mediaList, key = { it.id }) { anime ->
            MediaCard(media = anime, palettes = palettes, onClick = onMediaClick)
        }
    }
}

@Composable
fun CarouselMediaRelationsList(
    modifier: Modifier = Modifier,
    relations: List<MediaDetails.MediaRelation>,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit = {}
) {
    TvLazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .wrapContentHeight()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(horizontal = 58.dp)
    ) {
        items(relations, key = { it.media.id }) { relation ->
            MediaCard(
                media = relation.media,
                relationType = relation.relationType,
                palettes = palettes,
                onClick = onMediaClick
            )
        }
    }
}

@TvPreview
@Composable
fun PreviewCarousel(
    @PreviewParameter(MediaListPreviewParameterProvider::class) mediaList: List<Media>
) {
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)
    SekaiTheme {
        CarouselMediaList(
            mediaList = mediaList,
            palettes = palettes
        )
    }
}