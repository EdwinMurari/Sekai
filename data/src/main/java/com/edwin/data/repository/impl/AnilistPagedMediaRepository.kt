package com.edwin.data.repository.impl

import com.edwin.data.mapper.asAnilistModel
import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.getAnilistMediaSort
import com.edwin.data.model.MediaPageResponse
import com.edwin.data.model.SearchParams
import com.edwin.data.repository.PagedMediaRepository
import com.edwin.network.anilist.AnilistNetworkDataSource
import javax.inject.Inject

class AnilistPagedMediaRepository @Inject constructor(
    private val anilistDataSource: AnilistNetworkDataSource
) : PagedMediaRepository {

    override suspend fun getPagedMediaResults(
        page: Int,
        pageSize: Int,
        searchParams: SearchParams
    ): Result<MediaPageResponse> {
        val response = anilistDataSource.search(
            page = page,
            pageSize = pageSize,
            query = searchParams.query,
            formats = searchParams.format?.asAnilistModel(),
            status = searchParams.status?.asAnilistModel(),
            seasonYear = searchParams.seasonYear,
            season = searchParams.season?.asAnilistModel(),
            genres = searchParams.genres?.map { it.name },
            minScore = searchParams.minScore?.times(10),
            sort = searchParams.getAnilistMediaSort()?.let { listOf(it) }
        )

        return if (response.hasErrors())
            Result.failure(Exception(response.errors?.firstOrNull()?.message ?: "Unknown error"))
        else {
            response.data?.Page?.let { Result.success(it.asExternalModel()) } ?: Result.failure(
                Exception("No data")
            )
        }
    }
}
