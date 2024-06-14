package com.edwin.sekai.ui.feature.browse

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    enum class EnumAnimeCategory(val title: String) {
        TV_TRENDING_THIS_SEASON("Trending Anime This Season"),
        MOVIE_TRENDING_THIS_SEASON("Trending Movies This Season"),
        TV_POPULAR_THIS_SEASON("Popular Anime This Season"),
        TV_TOP_THIS_SEASON("Top Anime This Season"),
        TV_TRENDING("Trending Anime"),
        TV_POPULAR("Popular Anime"),
        TV_TOP("Top Anime"),
        MOVIE_POPULAR_THIS_SEASON("Popular Movies This Season"),
        MOVIE_TOP_THIS_SEASON("Top Movies This Season"),
        MOVIE_TRENDING("Trending Movies"),
        MOVIE_POPULAR("Popular Movies"),
        MOVIE_TOP("Top Movies")
    }

    data class Media(
        val id: String,
        val name: String
    )

    data class CategoryList(val category: EnumAnimeCategory, var list: List<Media>)

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val categories: List<CategoryList>) : UiState()
        data class Error(val exception: Throwable) : UiState()
    }
}