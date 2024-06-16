package com.edwin.data.model

import com.edwin.network.GetTrendingAndPopularQuery
import com.edwin.network.fragment.AnimeFragment
import com.edwin.network.fragment.MovieFragment

fun GetTrendingAndPopularQuery.Data.asExternalModel() = MediaCollections(
    trendingTvSeries = trendingAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    trendingMovies = trendingMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.toMovie() },
    popularTvSeries = popularAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    topTvSeriesThisSeason = topAnimeThisSeason?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    trendingTvSeriesAllTime = trendingAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    popularTvSeriesAllTime = popularAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    topTvSeriesAllTime = topAnimeAllTime?.media?.mapNotNull { it?.animeFragment?.toTvSeries() },
    popularMoviesThisSeason = popularMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.toMovie() },
    topMoviesThisSeason = topMoviesThisSeason?.media?.mapNotNull { it?.movieFragment?.toMovie() },
    trendingMoviesAllTime = trendingMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.toMovie() },
    popularMoviesAllTime = popularMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.toMovie() },
    topMoviesAllTime = topMoviesAllTime?.media?.mapNotNull { it?.movieFragment?.toMovie() }
)

fun AnimeFragment.toTvSeries() = Media.TvSeries(
    id = mediaFragment.id,
    title = (mediaFragment.title?.english ?: mediaFragment.title?.romaji).orEmpty(),
    coverImage = mediaFragment.coverImage?.large.orEmpty(),
    averageScore = mediaFragment.averageScore ?: 0,
    popularity = mediaFragment.popularity ?: 0,
    startDate = mediaFragment.startDate?.year ?: 0,
    episodes = episodes ?: 0,
    nextAiringEpisode = nextAiringEpisode?.toAnimeNextAiringEpisode()
)

private fun AnimeFragment.NextAiringEpisode?.toAnimeNextAiringEpisode() = this?.let {
    Media.TvSeries.NextAiringEpisode(
        episode = it.episode,
        timeUntilAiring = it.timeUntilAiring
    )
}

fun MovieFragment.toMovie() = Media.Movie(
    id = mediaFragment.id,
    title = (mediaFragment.title?.english ?: mediaFragment.title?.romaji).orEmpty(),
    coverImage = mediaFragment.coverImage?.large.orEmpty(),
    averageScore = mediaFragment.averageScore ?: 0,
    popularity = mediaFragment.popularity ?: 0,
    startDate = mediaFragment.startDate?.year ?: 0,
    duration = duration ?: 0
)

fun mapToNetworkMediaSeason(season: MediaSeason): com.edwin.network.type.MediaSeason {
    return when (season) {
        MediaSeason.WINTER -> com.edwin.network.type.MediaSeason.WINTER
        MediaSeason.SPRING -> com.edwin.network.type.MediaSeason.SPRING
        MediaSeason.SUMMER -> com.edwin.network.type.MediaSeason.SUMMER
        MediaSeason.FALL -> com.edwin.network.type.MediaSeason.FALL
    }
}