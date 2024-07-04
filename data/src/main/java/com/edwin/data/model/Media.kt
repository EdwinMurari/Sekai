package com.edwin.data.model

sealed class Media {
    abstract val id: Int
    abstract val title: String?
    abstract val description: String?
    abstract val coverImage: String?
    abstract val bannerImage: String?
    abstract val genres: List<String>?
    abstract val rawAverageScore: Int?
    abstract val popularity: Int?
    abstract val startDate: Int? // Year
    abstract val averageColorHex: String?

    val averageScoreOutOfTen: Float? get() = rawAverageScore?.toFloat()?.let { it / 10 }

    data class TvSeries(
        override val id: Int,
        override val title: String?,
        override val description: String?,
        override val coverImage: String?,
        override val bannerImage: String?,
        override val genres: List<String>?,
        override val rawAverageScore: Int?,
        override val popularity: Int?,
        override val startDate: Int?, // Year
        override val averageColorHex: String?,
        val episodesCount: Int?,
        val nextAiringEpisode: NextAiringEpisode? = null
    ) : Media() {

        data class NextAiringEpisode(
            val episode: Int,
            val timeUntilAiring: Int
        )
    }

    data class Movie(
        override val id: Int,
        override val title: String?,
        override val description: String?,
        override val coverImage: String?,
        override val bannerImage: String?,
        override val genres: List<String>?,
        override val rawAverageScore: Int?,
        override val popularity: Int?,
        override val startDate: Int?, // Year
        override val averageColorHex: String?,
        val duration: Int? // Minutes
    ) : Media()
}