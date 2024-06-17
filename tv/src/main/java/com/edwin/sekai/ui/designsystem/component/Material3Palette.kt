package com.edwin.sekai.ui.designsystem.component

import kotlinx.serialization.Serializable

@Serializable
data class Material3Palette(
    val name: String? = null,
    val primary: String? = null,
    val onPrimary: String? = null,
    val secondary: String? = null,
    val onSecondary: String? = null,
    val tertiary: String? = null,
    val onTertiary: String? = null,
    val error: String? = null,
    val onError: String? = null,
    val background: String? = null,
    val onBackground: String? = null,
    val surface: String? = null,
    val onSurface: String? = null
)