package com.edwin.data.model

sealed interface Extension {

    val name: String
    val pkgName: String
    val versionName: String
    val versionCode: Long
    val libVersion: Double
    val lang: String?
    val isNsfw: Boolean

    data class Available(
        override val name: String,
        override val pkgName: String,
        override val versionName: String,
        override val versionCode: Long,
        override val libVersion: Double,
        override val lang: String,
        override val isNsfw: Boolean,
        val sources: List<AnimeSource>,
        val apkName: String,
        val iconUrl: String,
        val repoUrl: String,
    ) : Extension {

        data class AnimeSource(
            val id: Long,
            val lang: String,
            val name: String,
            val baseUrl: String,
        )
    }
}
