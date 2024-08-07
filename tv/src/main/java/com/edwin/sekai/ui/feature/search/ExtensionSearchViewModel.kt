package com.edwin.sekai.ui.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.edwin.data.di.ExtensionPagedMediaRepo
import com.edwin.data.model.Media
import com.edwin.data.model.SearchParams
import com.edwin.data.model.SourceSelectionStatus
import com.edwin.data.pagingsource.GenericMediaPagingSource
import com.edwin.data.repository.PagedMediaRepository
import com.edwin.data.repository.impl.SourceRepository
import com.edwin.sekai.ui.feature.extension.navigation.ExtensionArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtensionSearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sourceRepo: SourceRepository,
    @ExtensionPagedMediaRepo private val pagedMediaRepository: PagedMediaRepository
) : BaseSearchViewModel(savedStateHandle) {


    private val extensionArgs = ExtensionArgs(savedStateHandle)
    private val pkgName = extensionArgs.pkgName

    private val _sourceStatus =
        MutableStateFlow<SourceSelectionStatus>(SourceSelectionStatus.Loading)

    init {
        viewModelScope.launch {
            _sourceStatus.value = try {
                sourceRepo.selectSource(pkgName)
            } catch (e: Exception) {
                SourceSelectionStatus.SourceUnavailable
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchMedia(searchParams: SearchParams): Flow<PagingData<Media>> {
        return _sourceStatus.flatMapLatest { status ->
            if (status is SourceSelectionStatus.SourceSelected) {
                Pager(
                    config = PagingConfig(pageSize = searchParams.perPage),
                    pagingSourceFactory = {
                        GenericMediaPagingSource(pagedMediaRepository, searchParams)
                    }
                ).flow
            } else {
                flowOf(PagingData.empty())
            }
        }
    }
}