package com.edwin.sekai.ui.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.edwin.data.di.AnilistPagedMediaRepo
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.data.pagingsource.GenericMediaPagingSource
import com.edwin.data.repository.PagedMediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @AnilistPagedMediaRepo private val pagedMediaRepository: PagedMediaRepository
) : BaseSearchViewModel(savedStateHandle) {

    override fun searchMedia(searchParams: SearchParams): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(pageSize = searchParams.perPage),
            pagingSourceFactory = {
                GenericMediaPagingSource(
                    pagedMediaRepository = pagedMediaRepository,
                    searchParams = searchParams
                )
            }
        ).flow
    }
}