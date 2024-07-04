package com.edwin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.asNetworkModel
import com.edwin.data.model.Media
import com.edwin.data.model.MediaCollections
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.model.SearchParams
import com.edwin.data.pagingsource.SearchMediaPagingSource
import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.jikan.JikanNetworkDataSource
import com.edwin.network.kitsu.KitsuNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class OneOffMediaRepository @Inject constructor(
    private val anilistDataSource: AnilistNetworkDataSource,
    private val jikanDataSource: JikanNetworkDataSource,
    private val kitsuDataSource: KitsuNetworkDataSource
) : MediaRepository {

    override fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ): Flow<NetworkResponse<MediaCollections>> = flow {
        val response = anilistDataSource.getTrendingAndPopularMedia(
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
        anilistDataSource.getMediaById(mediaId).map { response ->
            if (response.hasErrors()) {
                return@map NetworkResponse.Error(
                    response.errors?.map { Error(it.message) } ?: listOf(Error())
                )
            } else {
                return@map response.data?.Media?.mediaDetailsFragment?.let { mediaDetailsFragment ->
                    val jikanEpisodesResponse =
                        mediaDetailsFragment.idMal?.let { jikanDataSource.getEpisodeForAnime(it) }
                    val kitsuEpisodesResponse =
                        kitsuDataSource.getEpisodeForAnime(mediaId.toString())

                    mediaDetailsFragment.asExternalModel(
                        jikanResponse = jikanEpisodesResponse,
                        kitsuEpisodesResponse = kitsuEpisodesResponse
                    )?.let {
                        NetworkResponse.Success(it)
                    } ?: NetworkResponse.Error(listOf(Error("Unsupported format")))
                } ?: NetworkResponse.Error(listOf(Error("Media details not found")))
            }
        }

    override fun getPagedSearchResults(
        searchParams: SearchParams
    ): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = searchParams.perPage),
            pagingSourceFactory = {
                SearchMediaPagingSource(
                    anilistDataSource,
                    searchParams
                )
            }
        ).flow
    }
}
