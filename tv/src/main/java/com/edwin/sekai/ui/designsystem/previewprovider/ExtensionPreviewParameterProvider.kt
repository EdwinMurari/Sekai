package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel

class ExtensionPreviewParameterProvider  : PreviewParameterProvider<ExtensionUiModel> {
    override val values = sequenceOf(
        ExtensionUiModel.Installed(
            title = "Example Installed Extension",
            pkgName = "com.example.installedextension",
            version = "1.2.3",
            language = "English",
            isNsfw = false,
            icon = null,
            hasUpdate = false,
            isObsolete = false,
            iconUrl = "",
            apkUrl = ""
        ),
        ExtensionUiModel.Available(
            title = "Example Available Extension",
            pkgName = "com.example.availableextension",
            version = "4.5.6",
            language = "Spanish",
            isNsfw = false,
            iconUrl = "",
            apkUrl = ""
        ),
        ExtensionUiModel.Available(
            title = "Example Available Extension NSFW",
            pkgName = "com.example.availableextensionNsfw",
            version = "4.5.7",
            language = "English",
            isNsfw = true,
            iconUrl = "",
            apkUrl = ""
        ),
        ExtensionUiModel.Installed(
            title = "Example Installed Extension NSFW",
            pkgName = "com.example.installedextensionNsfw",
            version = "1.2.4",
            language = "English",
            isNsfw = true,
            icon = null,
            hasUpdate = false,
            isObsolete = false,
            iconUrl = "",
            apkUrl = ""
        ),
        ExtensionUiModel.Installed(
            title = "Example Installed Extension NSFW",
            pkgName = "com.example.installedextensionUpdate",
            version = "1.2.5",
            language = "English",
            isNsfw = true,
            icon = null,
            hasUpdate = true,
            isObsolete = false,
            iconUrl = "",
            apkUrl = ""
        ),
        ExtensionUiModel.Installed(
            title = "Example Installed Extension NSFW",
            pkgName = "com.example.installedextensionObsolete",
            version = "1.2.6",
            language = "English",
            isNsfw = true,
            icon = null,
            hasUpdate = false,
            isObsolete = true,
            iconUrl = "",
            apkUrl = ""
        ),
    )
}