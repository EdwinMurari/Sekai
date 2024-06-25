package com.edwin.network.anilist

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.network.anilist.type.MediaSeason
import kotlinx.coroutines.flow.Flow

interface AnilistNetworkDataSource {

    suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): ApolloResponse<GetTrendingAndPopularQuery.Data>

    fun getMediaById(
        mediaId: Int
    ): Flow<ApolloResponse<GetMediaDetailsByIdQuery.Data>>
}