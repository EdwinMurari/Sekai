package com.edwin.data.repository

import com.edwin.data.model.AllMediaList
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<AllMediaList>>
}