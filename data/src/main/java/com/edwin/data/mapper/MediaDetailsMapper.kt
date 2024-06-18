package com.edwin.data.mapper

import com.edwin.data.model.MediaDetails
import com.edwin.network.fragment.MediaDetailsFragment
import com.edwin.network.fragment.MediaFragment

fun MediaDetailsFragment.asExternalModel() = when {
    mediaFragment.format.isTvSeries() -> asExternalTvSeriesModel()
    mediaFragment.format.isMovie() -> asExternalMovieModel()
    else -> null
}

fun MediaDetailsFragment.asExternalTvSeriesModel() = MediaDetails.TvSeries(
    media = mediaFragment.asExternalTvSeriesModel(),
    fullTitle = mediaFragment.title?.asExternalModel(),
    relations = relations?.edges?.mapNotNull { it?.asExternalModel() },
    recommendations = recommendations?.edges?.mapNotNull { it?.asExternalModel() },
    episodes = streamingEpisodes?.mapNotNull { it?.asExternalModel() }
)

fun MediaDetailsFragment.asExternalMovieModel() = MediaDetails.Movie(
    media = mediaFragment.asExternalMovieModel(),
    fullTitle = mediaFragment.title?.asExternalModel(),
    relations = relations?.edges?.mapNotNull { it?.asExternalModel() },
    recommendations = recommendations?.edges?.mapNotNull { it?.asExternalModel() }
)


fun MediaFragment.Title.asExternalModel() = MediaDetails.Title(
    english = english,
    romaji = romaji,
    native = native
)

fun MediaDetailsFragment.StreamingEpisode.asExternalModel() = MediaDetails.Episode(
    number = 0, // TODO :: Get the episode data from another source get fill all the details
    title = title,
    thumbnail = thumbnail
)

fun MediaDetailsFragment.Edge.asExternalModel() = MediaDetails.MediaRelation(
    relationType = relationType?.name,
    media = node?.mediaFragment?.asExternalModel()
)

private fun MediaDetailsFragment.Edge1.asExternalModel() =
    node?.media?.mediaFragment?.asExternalModel()