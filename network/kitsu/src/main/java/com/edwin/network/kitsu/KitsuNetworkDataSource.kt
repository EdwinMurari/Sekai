package com.edwin.network.kitsu

import com.apollographql.apollo3.api.ApolloResponse

interface KitsuNetworkDataSource {

    suspend fun getEpisodeForAnime(anilistId: String): ApolloResponse<GetEpisodeForAnilistMediaIdQuery.Data>
}