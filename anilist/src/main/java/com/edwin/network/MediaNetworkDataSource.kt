package com.edwin.network

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.network.type.MediaSeason
import kotlinx.coroutines.flow.Flow

interface MediaNetworkDataSource {

    suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): ApolloResponse<GetTrendingAndPopularQuery.Data>

    fun getMediaById(
        mediaId: Int
    ): Flow<ApolloResponse<GetMediaDetailsByIdQuery.Data>>
}