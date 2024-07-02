package com.edwin.data.model

import com.edwin.network.anilist.type.MediaSort as NetworkMediaSort

data class SearchParams(
    val page: Int,
    val perPage: Int,
    val query: String? = null,
    val format: MediaFormat? = null,
    val status: MediaStatus? = null,
    val sortBy: MediaSort? = null,
    val order: Order = Order.DESCENDING,
    val genres: List<String>? = null,
    val tags: List<String>? = null,
    val minScore: Int? = null,
    val seasonYear: Int? = null,
    val season: MediaSeason? = null,
    val isAdult: Boolean? = null
) {
    fun getNetworkMediaSort() = when (sortBy to order) {
        MediaSort.TITLE to Order.ASCENDING -> NetworkMediaSort.TITLE_ENGLISH
        MediaSort.TITLE to Order.DESCENDING -> NetworkMediaSort.TITLE_ENGLISH_DESC

        MediaSort.POPULARITY to Order.ASCENDING -> NetworkMediaSort.POPULARITY
        MediaSort.POPULARITY to Order.DESCENDING -> NetworkMediaSort.POPULARITY_DESC

        MediaSort.START_DATE to Order.ASCENDING -> NetworkMediaSort.START_DATE
        MediaSort.START_DATE to Order.DESCENDING -> NetworkMediaSort.START_DATE_DESC

        MediaSort.SCORE to Order.ASCENDING -> NetworkMediaSort.SCORE
        MediaSort.SCORE to Order.DESCENDING -> NetworkMediaSort.SCORE_DESC

        MediaSort.TRENDING to Order.ASCENDING -> NetworkMediaSort.TRENDING
        MediaSort.TRENDING to Order.DESCENDING -> NetworkMediaSort.TRENDING_DESC

        MediaSort.EPISODES to Order.ASCENDING -> NetworkMediaSort.EPISODES
        MediaSort.EPISODES to Order.DESCENDING -> NetworkMediaSort.EPISODES_DESC

        MediaSort.DURATION to Order.ASCENDING -> NetworkMediaSort.DURATION
        MediaSort.DURATION to Order.DESCENDING -> NetworkMediaSort.DURATION_DESC

        MediaSort.FORMAT to Order.ASCENDING -> NetworkMediaSort.FORMAT
        MediaSort.FORMAT to Order.DESCENDING -> NetworkMediaSort.FORMAT_DESC

        else -> null
    }

    fun hasAppliedFilters(): Boolean {
        return format != null || status != null || sortBy != null ||
                !genres.isNullOrEmpty() || !tags.isNullOrEmpty() ||
                minScore != null || seasonYear != null || season != null || isAdult != null
    }

    fun getAppliedFilters(): List<Pair<String, Any?>> {
        val filters = mutableListOf<Pair<String, Any?>>()
        if (format != null) filters.add("Format" to format)
        if (status != null) filters.add("Status" to status)
        if (sortBy != null) filters.add("Sort By" to sortBy)
        if (!genres.isNullOrEmpty()) filters.add("Genres" to genres)
        if (!tags.isNullOrEmpty()) filters.add("Tags" to tags)
        if (minScore != null) filters.add("Min Score" to minScore)
        if (seasonYear != null) filters.add("Season Year" to seasonYear)
        if (season != null) filters.add("Season" to season)
        if (isAdult != null) filters.add("Adult" to isAdult)
        return filters
    }
}
