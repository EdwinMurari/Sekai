package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import com.edwin.data.model.Media

@Composable
fun CarouselMediaList(
    modifier: Modifier = Modifier,
    list: List<Media>,
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
        items(list, key = { it.id }) { anime ->
            MediaCard(media = anime, palettes = palettes, onClick = onMediaClick)
        }
    }
}