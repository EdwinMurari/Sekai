package com.edwin.network.anilist.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.Optional
import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.anilist.GetMediaDetailsByIdQuery
import com.edwin.network.anilist.GetTrendingAndPopularQuery
import com.edwin.network.anilist.SearchMediaQuery
import com.edwin.network.anilist.di.AniListApolloClient
import com.edwin.network.anilist.type.MediaFormat
import com.edwin.network.anilist.type.MediaSeason
import com.edwin.network.anilist.type.MediaSort
import com.edwin.network.anilist.type.MediaStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ApolloAnilistNetworkDataSource @Inject constructor(
    @AniListApolloClient private val apolloClient: ApolloClient
) : AnilistNetworkDataSource {

    override suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ) = apolloClient.query(
        GetTrendingAndPopularQuery(
            season,
            seasonYear
        )
    ).execute()

    override fun getMediaById(mediaId: Int) =
        apolloClient.executeAsFlow(ApolloRequest.Builder(GetMediaDetailsByIdQuery(mediaId)).build())

    override suspend fun search(
        pageSize: Int,
        page: Int,
        query: String?,
        formats: List<MediaFormat>?,
        status: MediaStatus?,
        seasonYear: Int?,
        season: MediaSeason?,
        genres: List<String>?,
        tags: List<String>?,
        minScore: Int?,
        sort: List<MediaSort>?
    ) = apolloClient.query(
        SearchMediaQuery(
            perPage = Optional.present(pageSize),
            page = Optional.present(page),
            search = Optional.presentIfNotNull(query),
            formats = Optional.presentIfNotNull(formats),
            status = Optional.presentIfNotNull(status),
            seasonYear = Optional.presentIfNotNull(seasonYear),
            season = Optional.presentIfNotNull(season),
            genres = Optional.presentIfNotNull(genres),
            tags = Optional.presentIfNotNull(tags),
            minimumScore = Optional.presentIfNotNull(minScore),
            sort = Optional.presentIfNotNull(sort)
        )
    ).execute()
}