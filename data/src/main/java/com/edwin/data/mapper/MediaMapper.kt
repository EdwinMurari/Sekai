package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.data.model.MediaPageResponse
import com.edwin.data.model.PageInfo
import com.edwin.network.anilist.SearchMediaQuery
import com.edwin.network.anilist.fragment.MediaFragment

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

private fun MediaFragment.NextAiringEpisode?.asExternalModel() = this?.let {
    Media.TvSeries.NextAiringEpisode(
        episode = it.episode,
        timeUntilAiring = it.timeUntilAiring
    )
}

private fun MediaFragment.getTitle() = title?.english ?: title?.romaji ?: title?.native
private fun MediaFragment.getCoverImage() = coverImage?.extraLarge?.takeIf { it.isNotBlank() }
private fun MediaFragment.getBannerImage() = bannerImage?.takeIf { it.isNotBlank() }
private fun MediaFragment.getGenres() = genres?.filterNotNull()?.takeIf { it.isNotEmpty() }
private fun MediaFragment.getCoverAverageHex(): String? {
    return coverImage?.color?.takeIf { it.isNotBlank() && it.firstOrNull() == '#' }
}

fun SearchMediaQuery.Page.asExternalModel(): MediaPageResponse {
    return MediaPageResponse(
        pageInfo = pageInfo?.asExternalModel() ?: PageInfo(hasNextPage = false),
        media = media?.mapNotNull { it?.mediaFragment?.asExternalModel() } ?: emptyList()
    )
}

private fun SearchMediaQuery.PageInfo.asExternalModel(): PageInfo {
    return PageInfo(hasNextPage = hasNextPage ?: false)
}