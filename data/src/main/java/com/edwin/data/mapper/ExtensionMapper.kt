package com.edwin.data.mapper

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import com.edwin.data.model.Extension
import com.edwin.network.extensions.model.ExtensionApiModel
import com.edwin.network.extensions.model.InstalledExtensionApiModel
import com.edwin.network.extensions.model.SourceApiModel

fun ExtensionApiModel.asExternalModel(repoUrl: String) = Extension.Available(
    name = name.substringAfter("Aniyomi: "),
    pkgName = pkg,
    versionName = version,
    versionCode = code.toLong(),
    libVersion = extractLibVersion(),
    lang = lang,
    isNsfw = nsfw == 1,
    sources = sourceApiModels?.mapNotNull { it.asExternalModel() }.orEmpty(),
    apkName = apk,
    iconUrl = "${repoUrl.removeSuffix("/index.min.json")}/icon/${pkg}.png",
    repoUrl = repoUrl,
    apkUrl = "${repoUrl.removeSuffix("/index.min.json")}/apk/${apk}"
)

fun ExtensionApiModel.extractLibVersion(): Double {
    return version.substringBeforeLast('.').toDouble()
}

fun SourceApiModel.asExternalModel() = baseUrl?.let {
    Extension.Available.AnimeSource(
        id = id.toLong(),
        lang = lang,
        name = name,
        baseUrl = it,
    )
}

fun installedExtensionExternalModel(
    context: Context,
    installedExtensionApiModel: InstalledExtensionApiModel,
    availableExtension: ExtensionApiModel,
    repoUrl: String
) = Extension.Installed(
    name = availableExtension.name.substringAfter("Aniyomi: "),
    pkgName = availableExtension.pkg,
    versionName = availableExtension.version,
    versionCode = availableExtension.code.toLong(),
    libVersion = availableExtension.extractLibVersion(),
    lang = availableExtension.lang,
    isNsfw = availableExtension.nsfw == 1,
    apkUrl = "${repoUrl.removeSuffix("/index.min.json")}/apk/${availableExtension.apk}",
    pkgFactory = installedExtensionApiModel.packageInfo.applicationInfo.metaData.getString(
        METADATA_SOURCE_FACTORY
    ),
    icon = installedExtensionApiModel.packageInfo.applicationInfo.loadIcon(context.packageManager),
    isShared = installedExtensionApiModel.isShared,
    iconUrl = "${repoUrl.removeSuffix("/index.min.json")}/icon/${availableExtension.pkg}.png",
    repoUrl = repoUrl,
    isObsolete = false,
    hasUpdate = updateExists(
        installedExtensionApiModel,
        availableExtension
    )
)

private fun updateExists(
    installedExtensionApiModel: InstalledExtensionApiModel,
    availableExtension: ExtensionApiModel,
): Boolean {
    return availableExtension.code > PackageInfoCompat.getLongVersionCode(installedExtensionApiModel.packageInfo)
}

fun InstalledExtensionApiModel.asExternalModel(
    context: Context,
    repoUrl: String
) = Extension.Installed(
    name = context.packageManager.getApplicationLabel(packageInfo.applicationInfo)
        .toString()
        .substringAfter("Aniyomi: "),
    pkgName = packageInfo.packageName,
    versionName = packageInfo.versionName,
    versionCode = PackageInfoCompat.getLongVersionCode(packageInfo),
    apkUrl = null,
    libVersion = packageInfo.versionName.substringBeforeLast('.').toDoubleOrNull() ?: 0.0,
    lang = "en",
    isNsfw = packageInfo.applicationInfo.metaData.getInt(METADATA_NSFW) == 1,
    pkgFactory = packageInfo.applicationInfo.metaData.getString(
        METADATA_SOURCE_FACTORY
    ),
    icon = packageInfo.applicationInfo.loadIcon(context.packageManager),
    isShared = isShared,
    iconUrl = "${repoUrl.removeSuffix("/index.min.json")}/icon/${packageInfo.packageName}.png",
    repoUrl = repoUrl,
    isObsolete = false,
    hasUpdate = false
)

fun mapExtensionExtensionAsExternalModel(
    context: Context,
    installedExtensions: List<InstalledExtensionApiModel>,
    availableExtensions: List<ExtensionApiModel>,
    repositoryUrl: String
): List<Extension> {
    val result = mutableListOf<Extension>()
    availableExtensions.forEach { availableExtension ->
        result.add(
            installedExtensions.find { it.packageInfo.packageName == availableExtension.pkg }
                ?.let { installedExtension ->
                    installedExtensionExternalModel(
                        context = context,
                        installedExtensionApiModel = installedExtension,
                        availableExtension = availableExtension,
                        repoUrl = repositoryUrl
                    )
                } ?: availableExtension.asExternalModel(repositoryUrl))
    }

    installedExtensions
        .filterNot { installedExtension -> result.any { it.pkgName == installedExtension.packageInfo.packageName } }
        .forEach { installedExtension ->
            result.add(installedExtension.asExternalModel(context, repositoryUrl))
        }

    return result
}

private const val METADATA_SOURCE_FACTORY = "tachiyomi.animeextension.factory"
private const val METADATA_NSFW = "tachiyomi.animeextension.nsfw"