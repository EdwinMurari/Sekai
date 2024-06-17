package com.edwin.data.repository

import com.edwin.data.model.MediaCollections
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.model.asExternalModel
import com.edwin.data.model.mapToNetworkMediaSeason
import com.edwin.network.MediaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class OneOffMediaRepository @Inject constructor(
    private val networkDataSource: MediaNetworkDataSource
) : MediaRepository {

    override fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<MediaCollections>> = flow {
        val response = networkDataSource.getTrendingAndPopularMedia(
            season = mapToNetworkMediaSeason(season),
            seasonYear = seasonYear
        )

        if (response.hasErrors()) {
            emit(NetworkResponse.Failure(
                response.errors?.map { Error(it.message) } ?: listOf(Error())
            ))
        } else {
            response.data?.let { data ->
                emit(NetworkResponse.Success(data.asExternalModel()))
            } ?: emit(NetworkResponse.Failure(listOf(Error())))
        }
    }.catch { exception ->
        emit(NetworkResponse.Failure(listOf(Error(exception.message))))
    }
}
