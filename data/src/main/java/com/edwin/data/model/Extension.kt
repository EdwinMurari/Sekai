package com.edwin.data.model

sealed class Extension {

    abstract val name: String
    abstract val pkgName: String
    abstract val versionName: String
    abstract val versionCode: Long
    abstract val libVersion: Double
    abstract val lang: String?
    abstract val isNsfw: Boolean

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
    ) : Extension() {

        data class AnimeSource(
            val id: Long,
            val lang: String,
            val name: String,
            val baseUrl: String,
        )
    }
}
