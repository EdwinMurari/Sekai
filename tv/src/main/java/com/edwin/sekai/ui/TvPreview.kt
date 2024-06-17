package com.edwin.sekai.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(device = "id:tv_1080p", name = "Tv",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_TELEVISION
)
annotation class TvPreview