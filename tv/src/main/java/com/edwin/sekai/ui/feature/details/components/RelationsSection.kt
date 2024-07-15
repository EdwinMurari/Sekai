package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.tv.foundation.lazy.list.TvLazyListScope
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.designsystem.component.CarouselMediaRelationsList
import com.edwin.sekai.ui.designsystem.component.Material3Palette

fun TvLazyListScope.relationsSection(
    contentPaddingValues: PaddingValues,
    mediaDetails: MediaDetails,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    if (!mediaDetails.relations.isNullOrEmpty()) {
        sectionHeader(
            text = "Related Content",
            modifier = Modifier.padding(contentPaddingValues)
        )
        item {
            CarouselMediaRelationsList(
                contentPaddingValues = contentPaddingValues,
                relations = mediaDetails.relations!!,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}