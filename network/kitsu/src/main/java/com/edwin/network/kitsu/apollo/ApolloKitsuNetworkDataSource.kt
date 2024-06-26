package com.edwin.network.kitsu.apollo

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.kitsu.GetEpisodeForAnilistMediaIdQuery
import com.edwin.network.kitsu.KitsuNetworkDataSource
import javax.inject.Inject

class ApolloKitsuNetworkDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : KitsuNetworkDataSource {

    // TODO :: Implemented paginated episodes
    override suspend fun getEpisodeForAnime(anilistId: String) =
        apolloClient.query(
            GetEpisodeForAnilistMediaIdQuery(anilistId)
        ).execute()
}