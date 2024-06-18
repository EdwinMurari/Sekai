package com.edwin.data.model

sealed class MediaDetails {
    abstract val media: Media
    abstract val fullTitle: Title?
    abstract val relations: List<MediaRelation>?
    abstract val recommendations: List<Media>?

    data class TvSeries(
        override val media: Media.TvSeries,
        override val fullTitle: Title?,
        override val relations: List<MediaRelation>?,
        override val recommendations: List<Media>?,
        val episodes: List<Episode>?
    ) : MediaDetails()

    data class Movie(
        override val media: Media.Movie,
        override val fullTitle: Title?,
        override val relations: List<MediaRelation>?,
        override val recommendations: List<Media>?
    ) : MediaDetails()

    data class Title(
        val english: String?,
        val romaji: String?,
        val native: String?
    )

    data class Episode(
        val title: String?,
        val thumbnail: String?
    )

    data class MediaRelation(
        val relationType: String?,
        val media: Media?
    )
}
