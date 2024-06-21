package com.edwin.network.anilist.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.edwin.network.anilist.GetMediaDetailsByIdQuery
import com.edwin.network.anilist.GetTrendingAndPopularQuery
import com.edwin.network.anilist.MediaNetworkDataSource
import com.edwin.network.anilist.type.MediaSeason
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AniListMediaNetwork @Inject constructor(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) : MediaNetworkDataSource {

    override suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ) = withContext(ioDispatcher) {
        apolloClient.query(
            GetTrendingAndPopularQuery(
                season,
                seasonYear
            )
        ).execute()
    }

    override fun getMediaById(mediaId: Int) =
        apolloClient.executeAsFlow(ApolloRequest.Builder(GetMediaDetailsByIdQuery(mediaId)).build())
}