package com.edwin.data.model

data class AllMediaList(
    val trendingTvSeries: List<TvSeries>?,
    val trendingMovies: List<Movie>?,
    val popularTvSeries: List<TvSeries>?,
    val topTvSeriesThisSeason: List<TvSeries>?,
    val trendingTvSeriesAllTime: List<TvSeries>?,
    val popularTvSeriesAllTime: List<TvSeries>?,
    val topTvSeriesAllTime: List<TvSeries>?,
    val popularMoviesThisSeason: List<Movie>?,
    val topMoviesThisSeason: List<Movie>?,
    val trendingMoviesAllTime: List<Movie>?,
    val popularMoviesAllTime: List<Movie>?,
    val topMoviesAllTime: List<Movie>?
)

data class TvSeries(
    val id: Int,
    val title: String,
    val coverImage: String,
    val averageScore: Int,
    val popularity: Int,
    val startDate: Int, // Year
    val episodes: Int,
    val nextAiringEpisode: NextAiringEpisode? = null
) {
    data class NextAiringEpisode(
        val episode: Int,
        val timeUntilAiring: Int
    )
}

data class Movie(
    val id: Int,
    val title: String,
    val coverImage: String,
    val averageScore: Int,
    val popularity: Int,
    val startDate: Int, // Year
    val duration: Int // Minutes
)