package com.edwin.sekai.ui.feature.details.components

import androidx.tv.foundation.lazy.list.TvLazyListScope
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.designsystem.component.CarouselMediaList
import com.edwin.sekai.ui.designsystem.component.Material3Palette

fun TvLazyListScope.recommendationsSection(
    mediaDetails: MediaDetails,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    if (!mediaDetails.relations.isNullOrEmpty()) {
        sectionHeader("Recommendations")
        item {
            CarouselMediaList(
                mediaList = mediaDetails.recommendations!!,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}