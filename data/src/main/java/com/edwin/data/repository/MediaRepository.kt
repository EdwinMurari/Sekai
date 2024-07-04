package com.edwin.data.repository

import androidx.paging.PagingData
import com.edwin.data.model.Media
import com.edwin.data.model.MediaCollections
import com.edwin.data.model.MediaDetails
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.model.SearchParams
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<MediaCollections>>

    fun getMediaById(mediaId: Int): Flow<NetworkResponse<MediaDetails>>

    fun getPagedSearchResults(searchParams: SearchParams): Flow<PagingData<Media>>
}