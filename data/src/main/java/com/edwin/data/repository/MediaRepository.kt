package com.edwin.data.repository

import com.edwin.data.model.MediaCollections
import com.edwin.data.model.MediaDetails
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<MediaCollections>>

    fun getMediaById(mediaId: Int): Flow<NetworkResponse<MediaDetails>>
}