package com.edwin.sekai.ui.feature.extensions.mapper

import com.edwin.data.model.Extension
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import java.util.Locale

fun Extension.asUiModel() = when (this) {
    is Extension.Available -> ExtensionUiModel.Available(
        iconUrl = iconUrl,
        title = name,
        language = getLanguageNameFromCode(lang),
        version = versionName,
        isNsfw = isNsfw,
        apkUrl = apkUrl,
        pkgName = pkgName
    )

    is Extension.Installed -> ExtensionUiModel.Installed(
        title = name,
        language = getLanguageNameFromCode(lang),
        version = versionName,
        isNsfw = isNsfw,
        icon = icon,
        iconUrl = iconUrl,
        pkgName = pkgName,
        hasUpdate = hasUpdate,
        isObsolete = isObsolete
    )
}

fun getLanguageNameFromCode(languageCode: String): String =
    Locale.forLanguageTag(languageCode).displayName