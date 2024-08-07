package com.edwin.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.data.repository.PagedMediaRepository
import javax.inject.Inject

class GenericMediaPagingSource @Inject constructor(
    private val pagedMediaRepository: PagedMediaRepository,
    private val searchParams: SearchParams
) : PagingSource<Int, Media>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val page = params.key ?: 1

        return try {
            val result = pagedMediaRepository.getPagedMediaResults(
                page = page,
                pageSize = params.loadSize,
                searchParams = searchParams
            )
            val successResult = result.getOrNull() ?: return LoadResult.Error(
                throwable = result.exceptionOrNull() ?: Exception("Unknown error")
            )

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (successResult.pageInfo.hasNextPage) page + 1 else null

            LoadResult.Page(
                data = successResult.media,
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
