package com.edwin.network.kitsu.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.edwin.network.kitsu.GetEpisodeForAnilistMediaIdQuery
import com.edwin.network.kitsu.KitsuNetworkDataSource
import javax.inject.Inject

class ApolloKitsuNetworkDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : KitsuNetworkDataSource {

    // TODO :: Implemented paginated episodes
    override fun getEpisodeForAnime(anilistId: String) =
        apolloClient.executeAsFlow(
            ApolloRequest.Builder(GetEpisodeForAnilistMediaIdQuery(anilistId)).build()
        )
}