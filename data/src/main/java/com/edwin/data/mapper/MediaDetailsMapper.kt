package com.edwin.data.mapper

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.data.model.MediaDetails
import com.edwin.network.anilist.fragment.MediaDetailsFragment
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.jikan.model.JikanEpisodeData
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
): List<MediaDetails.TvSeries.Episode>? {
    val jikanEpisodes = jikanEpisodesResponse?.data
        ?.takeUnless { it.isEmpty() }
        ?.map { it.asExternalModel() }
    val kitsuEpisodes = kitsuEpisodesResponse.data?.lookupMapping?.onAnime?.episodes?.nodes
        ?.takeUnless { it.isEmpty() }
        ?.mapNotNull { it?.asExternalModel() }

    return when {
        kitsuEpisodes != null && jikanEpisodes != null -> mergeEpisodes(
            kitsuEpisodes,
            jikanEpisodes
        )

        kitsuEpisodes != null -> kitsuEpisodes
        jikanEpisodes != null -> jikanEpisodes
        else -> null
    }
}

private fun mergeEpisodes(
    kitsuEpisodes: List<MediaDetails.TvSeries.Episode>,
    jikanEpisodes: List<MediaDetails.TvSeries.Episode>
): List<MediaDetails.TvSeries.Episode> {
    val jikanEpisodeMap = jikanEpisodes.associateBy { it.number }

    return (kitsuEpisodes.map { kitsuEpisode ->
        jikanEpisodeMap[kitsuEpisode.number]?.let { jikanEpisode ->
            kitsuEpisode.copy(
                title = kitsuEpisode.title ?: jikanEpisode.title,
                filler = jikanEpisode.filler,
                recap = jikanEpisode.recap
            )
        } ?: kitsuEpisode
    } + jikanEpisodes.filterNot { jikanEpisode ->  // Add unique Jikan episodes
        kitsuEpisodes.any { it.number == jikanEpisode.number }
    }).sortedBy { it.number }
}

private fun JikanEpisodeData.asExternalModel() = MediaDetails.TvSeries.Episode(
    number = malId,
    title = title ?: titleRomanji ?: titleJapanese,
    thumbnail = null,
    filler = filler,
    recap = recap,
    duration = null
)

private fun GetEpisodeForAnilistMediaIdQuery.Node.asExternalModel() = MediaDetails.TvSeries.Episode(
    number = number,
    title = titles.canonical,
    thumbnail = thumbnail?.original?.url,
    filler = false,
    recap = false,
    duration = length
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

fun MediaDetailsFragment.Edge.asExternalModel() =
    node?.mediaFragment?.asExternalModel()?.let { mediaFragment ->
        MediaDetails.MediaRelation(
            relationType = relationType?.name,
            media = mediaFragment
        )
    }

private fun MediaDetailsFragment.Edge1.asExternalModel() =
    node?.mediaRecommendation?.mediaFragment?.asExternalModel()