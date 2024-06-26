package com.edwin.network.kitsu

import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.Flow

interface KitsuNetworkDataSource {

    fun getEpisodeForAnime(anilistId: String): Flow<ApolloResponse<GetEpisodeForAnilistMediaIdQuery.Data>>
}