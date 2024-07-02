package com.edwin.network.anilist

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.network.anilist.type.MediaFormat
import com.edwin.network.anilist.type.MediaSeason
import com.edwin.network.anilist.type.MediaSort
import com.edwin.network.anilist.type.MediaStatus
import kotlinx.coroutines.flow.Flow

interface AnilistNetworkDataSource {

    suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): ApolloResponse<GetTrendingAndPopularQuery.Data>

    fun getMediaById(
        mediaId: Int
    ): Flow<ApolloResponse<GetMediaDetailsByIdQuery.Data>>

    suspend fun search(
        pageSize: Int,
        page: Int,
        query: String? = null,
        formats: List<MediaFormat>? = null,
        status: MediaStatus? = null,
        seasonYear: Int? = null,
        season: MediaSeason? = null,
        genres: List<String>? = null,
        tags: List<String>? = null,
        minScore: Int? = null,
        sort: List<MediaSort>? = null,
        isAdult: Boolean? = null
    ): ApolloResponse<SearchMediaQuery.Data>
}