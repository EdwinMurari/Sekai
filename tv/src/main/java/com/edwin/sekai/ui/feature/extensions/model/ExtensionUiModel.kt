package com.edwin.sekai.ui.feature.extensions.model

sealed interface ExtensionUiModel {
    val iconUrl: String
    val title: String
    val language: String
    val version: String
    val isNsfw: Boolean

    data class Available(
        override val iconUrl: String,
        override val title: String,
        override val language: String,
        override val version: String,
        override val isNsfw: Boolean,
        val apkUrl: String,
        val pkgName: String
    ) : ExtensionUiModel
}
