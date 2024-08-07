package com.edwin.data.repository.impl

import com.edwin.data.mapper.asExtensionModel
import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.getExtensionMediaSort
import com.edwin.data.model.MediaPageResponse
import com.edwin.data.model.SearchParams
import com.edwin.data.repository.PagedMediaRepository
import com.edwin.network.extensions.ExtensionDataSource
import javax.inject.Inject

class ExtensionPagedMediaRepository @Inject constructor(
    private val extensionDataSource: ExtensionDataSource
) : PagedMediaRepository {

    override suspend fun getPagedMediaResults(
        page: Int,
        pageSize: Int,
        searchParams: SearchParams
    ): Result<MediaPageResponse> {
        // TODO :: Temp solution
        val mediaPage = if (searchParams.query.isNullOrEmpty()) {
            extensionDataSource.fetchPopularAnime(page)
        } else {
            extensionDataSource.searchAnime(
                page = page,
                pageSize = pageSize,
                query = searchParams.query,
                formats = searchParams.format?.asExtensionModel(),
                status = searchParams.status?.asExtensionModel(),
                seasonYear = searchParams.seasonYear,
                season = searchParams.season?.asExtensionModel(),
                genres = searchParams.genres?.map { it.name },
                minScore = searchParams.minScore?.times(10),
                sort = searchParams.getExtensionMediaSort()?.let { listOf(it) }
            )
        }

        return mediaPage?.asExternalModel()?.let { Result.success(it) }
            ?: Result.failure(Exception("No data"))
    }
}
