package com.edwin.data.model

data class MediaCollections(
    val trendingTvSeries: List<Media.TvSeries>?,
    val trendingMovies: List<Media.Movie>?,
    val popularTvSeries: List<Media.TvSeries>?,
    val topTvSeries: List<Media.TvSeries>?,
    val trendingTvSeriesAllTime: List<Media.TvSeries>?,
    val popularTvSeriesAllTime: List<Media.TvSeries>?,
    val topTvSeriesAllTime: List<Media.TvSeries>?,
    val popularMovies: List<Media.Movie>?,
    val topMovies: List<Media.Movie>?,
    val trendingMoviesAllTime: List<Media.Movie>?,
    val popularMoviesAllTime: List<Media.Movie>?,
    val topMoviesAllTime: List<Media.Movie>?
)