package com.edwin.data.repository

import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.asNetworkModel
import com.edwin.data.model.MediaCollections
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.network.anilist.MediaNetworkDataSource
import com.edwin.network.jikan.JikanNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OneOffMediaRepository @Inject constructor(
    private val networkDataSource: MediaNetworkDataSource,
    private val jikanDataSource: JikanNetworkDataSource
) : MediaRepository {

    override fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<MediaCollections>> = flow {
        val response = networkDataSource.getTrendingAndPopularMedia(
            season = season.asNetworkModel(),
            seasonYear = seasonYear
        )

        if (response.hasErrors()) {
            emit(NetworkResponse.Error(
                response.errors?.map { Error(it.message) } ?: listOf(Error())
            ))
        } else {
            response.data?.let { data ->
                emit(NetworkResponse.Success(data.asExternalModel()))
            } ?: emit(NetworkResponse.Error(listOf(Error())))
        }
    }.catch { exception ->
        emit(NetworkResponse.Error(listOf(Error(exception.message))))
    }

    override fun getMediaById(mediaId: Int) =
        networkDataSource.getMediaById(mediaId).map { response ->
            if (response.hasErrors()) {
                return@map NetworkResponse.Error(
                    response.errors?.map { Error(it.message) } ?: listOf(Error())
                )
            } else {
                return@map response.data?.Media?.mediaDetailsFragment?.let { mediaDetailsFragment ->
                    val jikanEpisodesResponse =
                        mediaDetailsFragment.idMal?.let { jikanDataSource.getEpisodeForAnime(it) }

                    mediaDetailsFragment.asExternalModel(jikanEpisodesResponse)?.let {
                        NetworkResponse.Success(it)
                    } ?: NetworkResponse.Error(listOf(Error()))
                } ?: NetworkResponse.Error(listOf(Error()))
            }
        }
}
