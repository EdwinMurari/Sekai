package com.edwin.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edwin.data.mapper.asExternalModel
import com.edwin.data.mapper.asNetworkModel
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.network.anilist.AnilistNetworkDataSource
import javax.inject.Inject

class SearchMediaPagingSource @Inject constructor(
    private val anilistDataSource: AnilistNetworkDataSource,
    private val searchParams: SearchParams
) : PagingSource<Int, Media>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        // Start refresh at page 1 if undefined.
        val page = params.key ?: 1

        return try {
            val response = anilistDataSource.search(
                page = page,
                pageSize = params.loadSize,
                query = searchParams.query,
                formats = searchParams.format?.asNetworkModel(),
                status = searchParams.status?.asNetworkModel(),
                seasonYear = searchParams.seasonYear,
                season = searchParams.season?.asNetworkModel(),
                genres = searchParams.genres?.map { it.name },
                minScore = searchParams.minScore?.times(10),
                sort = searchParams.getNetworkMediaSort()?.let { listOf(it) }
            )

            val mediaList =
                response.data?.Page?.media?.mapNotNull { it?.mediaFragment?.asExternalModel() }
                    ?: emptyList()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.data?.Page?.pageInfo?.hasNextPage == true) page + 1 else null

            LoadResult.Page(
                data = mediaList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}