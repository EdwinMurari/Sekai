package com.edwin.data.mapper

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.data.model.MediaDetails
import com.edwin.network.anilist.fragment.MediaDetailsFragment
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.jikan.model.JikanEpisodesResponse
import com.edwin.network.kitsu.GetEpisodeForAnilistMediaIdQuery

fun MediaDetailsFragment.asExternalModel(
    jikanResponse: JikanEpisodesResponse?,
    kitsuEpisodesResponse: ApolloResponse<GetEpisodeForAnilistMediaIdQuery.Data>
) = when {
    mediaFragment.format.isTvSeries() -> asExternalTvSeriesModel(
        jikanResponse,
        kitsuEpisodesResponse
    )

    mediaFragment.format.isMovie() -> asExternalMovieModel()
    else -> null
}

fun MediaDetailsFragment.asExternalTvSeriesModel(
    jikanEpisodesResponse: JikanEpisodesResponse?,
    kitsuEpisodesResponse: ApolloResponse<GetEpisodeForAnilistMediaIdQuery.Data>
) =
    MediaDetails.TvSeries(
        media = mediaFragment.asExternalTvSeriesModel(),
        fullTitle = mediaFragment.title?.asExternalModel(),
        relations = relations?.edges?.mapNotNull { it?.asExternalModel() },
        recommendations = recommendations?.edges?.mapNotNull { it?.asExternalModel() },
        episodes = mapEpisodes(jikanEpisodesResponse, kitsuEpisodesResponse)
    )

private fun mapEpisodes(
    jikanEpisodesResponse: JikanEpisodesResponse?,
    kitsuEpisodesResponse: ApolloResponse<GetEpisodeForAnilistMediaIdQuery.Data>
): List<MediaDetails.Episode> {
    val jikanEpisodes = jikanEpisodesResponse?.data ?: emptyList()
    val kitsuEpisodes = kitsuEpisodesResponse.data?.lookupMapping?.onAnime?.episodes?.nodes
        ?: emptyList()

    val jikanEpisodeMap = jikanEpisodes.associateBy { it.malId }

    return kitsuEpisodes.mapNotNull { kitsuEpisode ->
        val episodeNumber = kitsuEpisode?.number ?: return@mapNotNull null
        val jikanEpisode = jikanEpisodeMap[episodeNumber]

        MediaDetails.Episode(
            number = episodeNumber,
            title = kitsuEpisode.titles.canonical,
            thumbnail = kitsuEpisode.thumbnail?.original?.url,
            filler = jikanEpisode?.filler ?: false,
            recap = jikanEpisode?.recap ?: false,
            duration = kitsuEpisode.length
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

fun MediaDetailsFragment.Edge.asExternalModel() =
    node?.mediaFragment?.asExternalModel()?.let { mediaFragment ->
        MediaDetails.MediaRelation(
            relationType = relationType?.name,
            media = mediaFragment
        )
    }

private fun MediaDetailsFragment.Edge1.asExternalModel() =
    node?.mediaRecommendation?.mediaFragment?.asExternalModel()