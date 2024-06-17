package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media

val sampleMediaList = MediaPreviewParameterProvider().values.toList()

class MediaListPreviewParameterProvider : PreviewParameterProvider<List<Media>> {
    override val values = sequenceOf(sampleMediaList)
}