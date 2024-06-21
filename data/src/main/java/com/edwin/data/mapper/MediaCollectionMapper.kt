package com.edwin.data.mapper

import com.edwin.data.model.MediaCollections
import com.edwin.network.anilist.GetTrendingAndPopularQuery

fun GetTrendingAndPopularQuery.Data.asExternalModel() = MediaCollections(
    trendingTvSeries = trendingAnimeThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMovies = trendingMoviesThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeries = popularAnimeThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeries = topAnimeThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingTvSeriesAllTime = trendingAnimeAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeriesAllTime = popularAnimeAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeriesAllTime = topAnimeAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalTvSeriesModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMovies = popularMoviesThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() },
    topMovies = topMoviesThisSeason?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMoviesAllTime = trendingMoviesAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMoviesAllTime = popularMoviesAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() },
    topMoviesAllTime = topMoviesAllTime?.media
        ?.mapNotNull { it?.mediaFragment?.asExternalMovieModel() }
        ?.takeIf { it.isNotEmpty() }
)