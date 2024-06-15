package com.edwin.network

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.network.type.MediaSeason

interface MediaNetworkDataSource {

    suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): ApolloResponse<GetTrendingAndPopularQuery.Data>
}