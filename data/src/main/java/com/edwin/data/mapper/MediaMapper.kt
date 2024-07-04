package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaStatus
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.anilist.type.MediaFormat as NetworkMediaFormat
import com.edwin.network.anilist.type.MediaSeason as NetworkMediaSeason
import com.edwin.network.anilist.type.MediaStatus as NetworkMediaStatus

val tvNetworkFormats = listOf(
    NetworkMediaFormat.TV,
    NetworkMediaFormat.TV_SHORT,
    NetworkMediaFormat.OVA,
    NetworkMediaFormat.ONA,
    NetworkMediaFormat.SPECIAL
)
val movieNetworkFormats = listOf(NetworkMediaFormat.MOVIE)

fun MediaFragment.asExternalTvSeriesModel() = Media.TvSeries(
    id = id,
    title = getTitle(),
    description = description,
    coverImage = getCoverImage(),
    bannerImage = getBannerImage(),
    genres = getGenres(),
    rawAverageScore = averageScore,
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
    rawAverageScore = averageScore,
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

fun NetworkMediaFormat?.isTvSeries() = this in tvNetworkFormats
fun NetworkMediaFormat?.isMovie() = this in movieNetworkFormats

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

fun MediaFormat.asNetworkModel() = when (this) {
    MediaFormat.TV -> tvNetworkFormats
    MediaFormat.MOVIE -> movieNetworkFormats
}

fun MediaStatus.asNetworkModel() = when (this) {
    MediaStatus.FINISHED -> NetworkMediaStatus.FINISHED
    MediaStatus.RELEASING -> NetworkMediaStatus.RELEASING
    MediaStatus.NOT_YET_RELEASED -> NetworkMediaStatus.NOT_YET_RELEASED
    MediaStatus.CANCELLED -> NetworkMediaStatus.CANCELLED
    MediaStatus.HIATUS -> NetworkMediaStatus.HIATUS
}