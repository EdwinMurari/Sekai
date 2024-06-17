package com.edwin.data.model

import com.edwin.network.GetTrendingAndPopularQuery
import com.edwin.network.fragment.AnimeFragment
import com.edwin.network.fragment.MediaFragment
import com.edwin.network.fragment.MovieFragment
import com.edwin.network.type.MediaSeason as NetworkMediaSeason

fun GetTrendingAndPopularQuery.Data.asExternalModel() = MediaCollections(
    trendingTvSeries = trendingAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    trendingMovies = trendingMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() },
    popularTvSeries = popularAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    topTvSeries = topAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    trendingTvSeriesAllTime = trendingAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    popularTvSeriesAllTime = popularAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    topTvSeriesAllTime = topAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() },
    popularMovies = popularMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() },
    topMovies = topMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() },
    trendingMoviesAllTime = trendingMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() },
    popularMoviesAllTime = popularMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() },
    topMoviesAllTime = topMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
)

fun AnimeFragment.asExternalModel() = Media.TvSeries(
    id = mediaFragment.id,
    title = mediaFragment.getTitle(),
    description = mediaFragment.description,
    coverImage = mediaFragment.coverImage?.large.takeUnless { it.isNullOrBlank() },
    bannerImage = mediaFragment.bannerImage.takeUnless { it.isNullOrBlank() },
    averageScore = mediaFragment.averageScore,
    popularity = mediaFragment.popularity,
    startDate = mediaFragment.startDate?.year,
    averageColorHex = mediaFragment.coverImage?.color.takeUnless { it.isNullOrBlank() },
    episodesCount = episodes,
    nextAiringEpisode = nextAiringEpisode?.asExternalModel()
)

private fun MediaFragment.getTitle() = title?.english ?: title?.romaji ?: title?.native

private fun AnimeFragment.NextAiringEpisode?.asExternalModel() = this?.let {
    Media.TvSeries.NextAiringEpisode(
        episode = it.episode,
        timeUntilAiring = it.timeUntilAiring
    )
}

fun MovieFragment.asExternalModel() = Media.Movie(
    id = mediaFragment.id,
    title = mediaFragment.getTitle(),
    description = mediaFragment.description,
    coverImage = mediaFragment.coverImage?.large.takeUnless { it.isNullOrBlank() },
    bannerImage = mediaFragment.bannerImage.takeUnless { it.isNullOrBlank() },
    averageScore = mediaFragment.averageScore,
    popularity = mediaFragment.popularity,
    startDate = mediaFragment.startDate?.year,
    averageColorHex = mediaFragment.coverImage?.color.takeUnless { it.isNullOrBlank() },
    duration = duration
)

fun MediaSeason.asNetworkModel(): NetworkMediaSeason {
    return when (this) {
        MediaSeason.WINTER -> NetworkMediaSeason.WINTER
        MediaSeason.SPRING -> NetworkMediaSeason.SPRING
        MediaSeason.SUMMER -> NetworkMediaSeason.SUMMER
        MediaSeason.FALL -> NetworkMediaSeason.FALL
    }
}