package com.edwin.network.anilist.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.anilist.GetMediaDetailsByIdQuery
import com.edwin.network.anilist.GetTrendingAndPopularQuery
import com.edwin.network.anilist.type.MediaSeason
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ApolloAnilistNetworkDataSource @Inject constructor(
    private val apolloClient: ApolloClient
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
}