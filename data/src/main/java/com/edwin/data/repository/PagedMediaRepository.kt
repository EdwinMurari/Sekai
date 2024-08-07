package com.edwin.data.repository

import com.edwin.data.model.SearchParams
import com.edwin.data.model.MediaPageResponse

interface PagedMediaRepository {

    suspend fun getPagedMediaResults(
        page: Int,
        pageSize: Int,
        searchParams: SearchParams
    ): Result<MediaPageResponse>
}