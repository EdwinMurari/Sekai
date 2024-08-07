package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.MediaDetails

class EpisodePreviewParameterProvider : PreviewParameterProvider<MediaDetails.TvSeries.Episode> {
    override val values = PreviewParameterData.episodes.asSequence()
}