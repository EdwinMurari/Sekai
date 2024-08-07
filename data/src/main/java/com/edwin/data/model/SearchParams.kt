package com.edwin.data.model

data class SearchParams(
    val page: Int,
    val perPage: Int,
    val query: String? = null,
    val format: MediaFormat? = null,
    val status: MediaStatus? = null,
    val sortBy: MediaSort? = null,
    val order: Order = Order.DESCENDING,
    val genres: List<Genre>? = null,
    val minScore: Int? = null,
    val seasonYear: Int? = null,
    val season: MediaSeason? = null,
    val isAdult: Boolean? = null
)