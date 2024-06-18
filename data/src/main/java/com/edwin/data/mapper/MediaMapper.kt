package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.data.model.MediaSeason
import com.edwin.network.fragment.MediaFragment
import com.edwin.network.type.MediaFormat as NetworkMediaFormat
import com.edwin.network.type.MediaSeason as NetworkMediaSeason

fun MediaFragment.asExternalTvSeriesModel() = Media.TvSeries(
    id = id,
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
    nextAiringEpisode = nextAiringEpisode.asExternalModel()
)

fun MediaFragment.asExternalMovieModel() = Media.Movie(
    id = id,
    title = getTitle(),
    description = description,
    coverImage = getCoverImage(),
    bannerImage = getBannerImage(),
    genres = getGenres(),
    averageScore = averageScore,
    popularity = popularity,
    startDate = startDate?.year,
    averageColorHex = getCoverAverageHex(),
    duration = duration
)

fun MediaFragment.asExternalModel() = when {
    format.isTvSeries() -> asExternalTvSeriesModel()
    format.isMovie() -> asExternalMovieModel()
    else -> null
}

fun NetworkMediaFormat?.isTvSeries() = this in listOf(
    NetworkMediaFormat.TV,
    NetworkMediaFormat.TV_SHORT,
    NetworkMediaFormat.OVA,
    NetworkMediaFormat.ONA,
    NetworkMediaFormat.SPECIAL
)

fun NetworkMediaFormat?.isMovie() = this in listOf(NetworkMediaFormat.MOVIE)

private fun MediaFragment.NextAiringEpisode?.asExternalModel() = this?.let {
    Media.TvSeries.NextAiringEpisode(
        episode = it.episode,
        timeUntilAiring = it.timeUntilAiring
    )
}

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