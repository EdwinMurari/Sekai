package com.edwin.data.model

data class MediaCollections(
    val trendingTvSeries: List<Media>?,
    val trendingMovies: List<Media>?,
    val popularTvSeries: List<Media>?,
    val topTvSeries: List<Media>?,
    val trendingTvSeriesAllTime: List<Media>?,
    val popularTvSeriesAllTime: List<Media>?,
    val topTvSeriesAllTime: List<Media>?,
    val popularMovies: List<Media>?,
    val topMovies: List<Media>?,
    val trendingMoviesAllTime: List<Media>?,
    val popularMoviesAllTime: List<Media>?,
    val topMoviesAllTime: List<Media>?
)

data class Media(
    val id: Int,
    val mediaFormat: MediaFormat?,
    val title: String?,
    val description: String?,
    val coverImage: String?,
    val bannerImage: String?,
    val genres: List<String>?,
    val averageScore: Int?,
    val popularity: Int?,
    val startDate: Int?, // Year
    val averageColorHex: String?,
    val episodesCount: Int?,
    val nextAiringEpisode: NextAiringEpisode? = null,
    val duration: Int? // Minutes
) {
    data class NextAiringEpisode(
        val episode: Int,
        val timeUntilAiring: Int
    )
}