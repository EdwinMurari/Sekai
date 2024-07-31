package com.edwin.sekai.ui.feature.extensions.model

import android.graphics.drawable.Drawable

sealed interface ExtensionUiModel {
    val iconUrl: String
    val title: String
    val language: String
    val version: String
    val isNsfw: Boolean
    val pkgName: String
    val apkUrl: String?

    data class Available(
        override val iconUrl: String,
        override val title: String,
        override val language: String,
        override val version: String,
        override val isNsfw: Boolean,
        override val pkgName: String,
        override val apkUrl: String,
    ) : ExtensionUiModel

    data class Installed(
        override val iconUrl: String,
        override val title: String,
        override val language: String,
        override val version: String,
        override val isNsfw: Boolean,
        override val pkgName: String,
        override val apkUrl: String?,
        val icon: Drawable?,
        val hasUpdate: Boolean,
        val isObsolete: Boolean,
    ) : ExtensionUiModel
}
