package com.edwin.sekai.ui.feature.extensions.mapper

import com.edwin.data.model.Extension
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel

fun Extension.toUiModel() = when (this) {
    is Extension.Available -> ExtensionUiModel.Available(
        iconUrl = iconUrl,
        title = name,
        language = lang,
        version = "$versionName ($versionCode)"
    )
}