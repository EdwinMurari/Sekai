package com.edwin.data.model

data class MediaDetails(
    val media: Media,
    val episodes: List<Episode>?,
    val relations: List<MediaRelation>?,
    val recommendations: List<MediaRecommendation>?
) {
    data class Episode(
        val title: String?,
        val thumbnail: String?
    )

    data class MediaRelation(
        val relationType: String?,
        val node: Media?
    )

    data class MediaRecommendation(
        val media: Media?
    )
}
