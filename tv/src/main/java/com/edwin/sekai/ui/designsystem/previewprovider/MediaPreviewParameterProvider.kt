package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media

class MediaPreviewParameterProvider : PreviewParameterProvider<Media> {
    override val values =
        (PreviewParameterData.tvSeriesList + PreviewParameterData.movieList).asSequence()
}