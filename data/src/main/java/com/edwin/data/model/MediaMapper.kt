package com.edwin.data.model

import com.edwin.network.GetTrendingAndPopularQuery
import com.edwin.network.fragment.AnimeFragment
import com.edwin.network.fragment.MediaFragment
import com.edwin.network.fragment.MovieFragment
import com.edwin.network.type.MediaSeason as NetworkMediaSeason

fun GetTrendingAndPopularQuery.Data.asExternalModel() = MediaCollections(
    trendingTvSeries = trendingAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMovies = trendingMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeries = popularAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeries = topAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingTvSeriesAllTime = trendingAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeriesAllTime = popularAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeriesAllTime = topAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMovies = popularMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topMovies = topMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMoviesAllTime = trendingMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMoviesAllTime = popularMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topMoviesAllTime = topMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() }
)

fun AnimeFragment.asExternalModel() = Media.TvSeries(
    id = mediaFragment.id,
    title = mediaFragment.getTitle(),
    description = mediaFragment.description,
    coverImage = mediaFragment.getCoverImage(),
    bannerImage = mediaFragment.getBannerImage(),
    genres = mediaFragment.getGenres(),
    averageScore = mediaFragment.averageScore,
    popularity = mediaFragment.popularity,
    startDate = mediaFragment.startDate?.year,
    averageColorHex = mediaFragment.getCoverAverageHex(),
    episodesCount = episodes,
    nextAiringEpisode = nextAiringEpisode?.asExternalModel()
)

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
    coverImage = mediaFragment.getCoverImage(),
    bannerImage = mediaFragment.getBannerImage(),
    genres = mediaFragment.getGenres(),
    averageScore = mediaFragment.averageScore,
    popularity = mediaFragment.popularity,
    startDate = mediaFragment.startDate?.year,
    averageColorHex = mediaFragment.getCoverAverageHex(),
    duration = duration
)

private fun MediaFragment.getTitle() = title?.english ?: title?.romaji ?: title?.native
private fun MediaFragment.getCoverImage() = coverImage?.large?.takeIf { it.isNotBlank() }
private fun MediaFragment.getBannerImage() = bannerImage?.takeIf { it.isNotBlank() }
private fun MediaFragment.getGenres() = genres?.filterNotNull()?.takeIf { it.isNotEmpty() }
private fun MediaFragment.getCoverAverageHex(): String? {
    return coverImage?.color?.takeIf { it.isNotBlank() && it.firstOrNull() == '#' }
}

fun MediaSeason.asNetworkModel(): NetworkMediaSeason {
    return when (this) {
        MediaSeason.WINTER -> NetworkMediaSeason.WINTER
        MediaSeason.SPRING -> NetworkMediaSeason.SPRING
        MediaSeason.SUMMER -> NetworkMediaSeason.SUMMER
        MediaSeason.FALL -> NetworkMediaSeason.FALL
    }
}