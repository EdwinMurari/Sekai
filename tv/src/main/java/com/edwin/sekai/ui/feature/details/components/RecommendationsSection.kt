package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.tv.foundation.lazy.list.TvLazyListScope
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.ui.designsystem.component.CarouselMediaList
import com.edwin.sekai.ui.designsystem.component.Material3Palette

fun TvLazyListScope.recommendationsSection(
    contentPaddingValues: PaddingValues,
    mediaDetails: MediaDetails,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    if (!mediaDetails.recommendations.isNullOrEmpty()) {
        sectionHeader(
            text = "Recommendations",
            modifier = Modifier.padding(contentPaddingValues)
        )

        item {
            CarouselMediaList(
                contentPaddingValues = contentPaddingValues,
                mediaList = mediaDetails.recommendations!!,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }
    }
}