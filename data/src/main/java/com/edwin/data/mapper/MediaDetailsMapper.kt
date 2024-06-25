package com.edwin.data.mapper

import com.edwin.data.model.MediaDetails
import com.edwin.network.anilist.fragment.MediaDetailsFragment
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.jikan.model.JikanEpisodesResponse

fun MediaDetailsFragment.asExternalModel(jikanResponse: JikanEpisodesResponse?) = when {
    mediaFragment.format.isTvSeries() -> asExternalTvSeriesModel(jikanResponse)
    mediaFragment.format.isMovie() -> asExternalMovieModel()
    else -> null
}

fun MediaDetailsFragment.asExternalTvSeriesModel(jikanEpisodesResponse: JikanEpisodesResponse?) =
    MediaDetails.TvSeries(
        media = mediaFragment.asExternalTvSeriesModel(),
        fullTitle = mediaFragment.title?.asExternalModel(),
        relations = relations?.edges?.mapNotNull { it?.asExternalModel() },
        recommendations = recommendations?.edges?.mapNotNull { it?.asExternalModel() },
        episodes = jikanEpisodesResponse?.asExternalModel()
    )

private fun JikanEpisodesResponse?.asExternalModel(): List<MediaDetails.Episode>? {
    return this?.data?.map {
        MediaDetails.Episode(
            number = it.malId,
            title = it.title ?: it.titleRomanji ?: it.titleJapanese,
            filler = it.filler
        )
    }
}

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

fun MediaDetailsFragment.Edge.asExternalModel() =
    node?.mediaFragment?.asExternalModel()?.let { mediaFragment ->
        MediaDetails.MediaRelation(
            relationType = relationType?.name,
            media = mediaFragment
        )
    }

private fun MediaDetailsFragment.Edge1.asExternalModel() =
    node?.mediaRecommendation?.mediaFragment?.asExternalModel()