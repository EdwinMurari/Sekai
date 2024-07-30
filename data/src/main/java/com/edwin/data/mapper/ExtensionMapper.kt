package com.edwin.data.mapper

import com.edwin.data.model.Extension
import com.edwin.network.extensions.model.ExtensionApiModel
import com.edwin.network.extensions.model.SourceApiModel

fun ExtensionApiModel.asExternalModel(repoUrl: String) = Extension.Available(
    name = name.substringAfter("Aniyomi: "),
    pkgName = pkg,
    versionName = version,
    versionCode = code.toLong(),
    libVersion = extractLibVersion(),
    lang = lang,
    isNsfw = nsfw == 1,
    sources = sourceApiModels
        ?.mapNotNull { it.asExternalModel() }
        .orEmpty(),
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