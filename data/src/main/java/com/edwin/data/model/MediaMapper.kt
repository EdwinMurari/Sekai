package com.edwin.data.model

import com.edwin.network.GetTrendingAndPopularQuery
import com.edwin.network.fragment.MediaDetailsFragment
import com.edwin.network.fragment.MediaFragment
import com.edwin.network.type.MediaFormat as NetworkMediaFormat
import com.edwin.network.type.MediaSeason as NetworkMediaSeason

fun GetTrendingAndPopularQuery.Data.asExternalModel() = MediaCollections(
    trendingTvSeries = trendingAnimeThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMovies = trendingMoviesThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeries = popularAnimeThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeries = topAnimeThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingTvSeriesAllTime = trendingAnimeAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularTvSeriesAllTime = popularAnimeAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topTvSeriesAllTime = topAnimeAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMovies = popularMoviesThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topMovies = topMoviesThisSeason?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    trendingMoviesAllTime = trendingMoviesAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    popularMoviesAllTime = popularMoviesAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() },
    topMoviesAllTime = topMoviesAllTime?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
        ?.takeIf { it.isNotEmpty() }
)

fun MediaFragment.asExternalModel() = Media(
    id = id,
    mediaFormat = format?.asExternalModel(),
    title = getTitle(),
    description = description,
    coverImage = getCoverImage(),
    bannerImage = getBannerImage(),
    genres = getGenres(),
    averageScore = averageScore,
    popularity = popularity,
    startDate = startDate?.year,
    averageColorHex = getCoverAverageHex(),
    episodesCount = episodes,
    nextAiringEpisode = nextAiringEpisode.asExternalModel(),
    duration = duration
)

private fun NetworkMediaFormat.asExternalModel() = when (this) {
    NetworkMediaFormat.TV -> MediaFormat.TV
    NetworkMediaFormat.TV_SHORT -> MediaFormat.TV_SHORT
    NetworkMediaFormat.MOVIE -> MediaFormat.MOVIE
    NetworkMediaFormat.SPECIAL -> MediaFormat.SPECIAL
    NetworkMediaFormat.OVA -> MediaFormat.OVA
    NetworkMediaFormat.ONA -> MediaFormat.ONA
    NetworkMediaFormat.MUSIC -> MediaFormat.MUSIC
    NetworkMediaFormat.MANGA -> MediaFormat.MANGA
    NetworkMediaFormat.NOVEL -> MediaFormat.NOVEL
    NetworkMediaFormat.ONE_SHOT -> MediaFormat.ONE_SHOT
    else -> null
}

private fun MediaFragment.NextAiringEpisode?.asExternalModel() = this?.let {
    Media.NextAiringEpisode(
        episode = it.episode,
        timeUntilAiring = it.timeUntilAiring
    )
}

fun MediaDetailsFragment.asExternalModel() = MediaDetails(
    media = mediaFragment.asExternalModel(),
    episodes = streamingEpisodes?.mapNotNull { it?.asExternalModel() },
    relations = relations?.edges?.mapNotNull { it?.asExternalModel() },
    recommendations = recommendations?.edges?.mapNotNull { it?.asExternalModel() }
)

fun MediaDetailsFragment.StreamingEpisode.asExternalModel() = MediaDetails.Episode(
    title = title,
    thumbnail = thumbnail
)

fun MediaDetailsFragment.Edge.asExternalModel() = MediaDetails.MediaRelation(
    relationType = relationType?.name,
    node = node?.mediaFragment?.asExternalModel()
)

private fun MediaDetailsFragment.Edge1.asExternalModel() = MediaDetails.MediaRecommendation(
    media = node?.media?.mediaFragment?.asExternalModel()
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