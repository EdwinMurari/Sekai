package com.edwin.data.model

data class MediaCollections(
    val trendingTvSeries: List<Media.TvSeries>?,
    val trendingMovies: List<Media.Movie>?,
    val popularTvSeries: List<Media.TvSeries>?,
    val topTvSeriesThisSeason: List<Media.TvSeries>?,
    val trendingTvSeriesAllTime: List<Media.TvSeries>?,
    val popularTvSeriesAllTime: List<Media.TvSeries>?,
    val topTvSeriesAllTime: List<Media.TvSeries>?,
    val popularMoviesThisSeason: List<Media.Movie>?,
    val topMoviesThisSeason: List<Media.Movie>?,
    val trendingMoviesAllTime: List<Media.Movie>?,
    val popularMoviesAllTime: List<Media.Movie>?,
    val topMoviesAllTime: List<Media.Movie>?
)

sealed class Media(
    open val id: Int,
    open val title: String,
    open val coverImage: String,
    open val averageScore: Int?,
    open val popularity: Int,
    open val startDate: Int, // Year
    open val averageColorHex: String
) {

    data class TvSeries(
        override val id: Int,
        override val title: String,
        override val coverImage: String,
        override val averageScore: Int?,
        override val popularity: Int,
        override val startDate: Int, // Year
        override val averageColorHex: String,
        val episodes: Int,
        val nextAiringEpisode: NextAiringEpisode? = null
    ) : Media(id, title, coverImage, averageScore, popularity, startDate, averageColorHex) {

        data class NextAiringEpisode(
            val episode: Int,
            val timeUntilAiring: Int
        )
    }

    data class Movie(
        override val id: Int,
        override val title: String,
        override val coverImage: String,
        override val averageScore: Int?,
        override val popularity: Int,
        override val startDate: Int, // Year
        override val averageColorHex: String,
        val duration: Int // Minutes
    ) : Media(id, title, coverImage, averageScore, popularity, startDate, averageColorHex)
}