package com.edwin.sekai.ui.feature.extensions.mapper

import com.edwin.data.model.Extension
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import java.util.Locale

fun Extension.toUiModel() = when (this) {
    is Extension.Available -> ExtensionUiModel.Available(
        iconUrl = iconUrl,
        title = name,
        language = getLanguageNameFromCode(lang),
        version = versionName,
        isNsfw = isNsfw,
        apkUrl = apkUrl,
        pkgName = pkgName
    )
}

fun getLanguageNameFromCode(languageCode: String): String =
    Locale.forLanguageTag(languageCode).displayName