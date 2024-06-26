package com.edwin.network.kitsu.apollo

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.kitsu.GetEpisodeForAnilistMediaIdQuery
import com.edwin.network.kitsu.KitsuNetworkDataSource
import com.edwin.network.kitsu.di.KitsuApolloClient
import javax.inject.Inject

class ApolloKitsuNetworkDataSource @Inject constructor(
    @KitsuApolloClient private val apolloClient: ApolloClient
) : KitsuNetworkDataSource {

    // TODO :: Implemented paginated episodes
    override suspend fun getEpisodeForAnime(anilistId: String) =
        apolloClient.query(
            GetEpisodeForAnilistMediaIdQuery(anilistId)
        ).execute()
}