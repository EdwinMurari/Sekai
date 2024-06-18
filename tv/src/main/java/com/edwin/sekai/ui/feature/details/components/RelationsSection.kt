package com.edwin.sekai.ui.feature.details.components

import androidx.tv.foundation.lazy.list.TvLazyListScope
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.designsystem.component.CarouselMediaRelationsList
import com.edwin.sekai.ui.designsystem.component.Material3Palette

fun TvLazyListScope.relationsSection(
    mediaDetails: MediaDetails,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    if (!mediaDetails.relations.isNullOrEmpty()) {
        sectionHeader("Related Content")
        item {
            CarouselMediaRelationsList(
                relations = mediaDetails.relations!!,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}