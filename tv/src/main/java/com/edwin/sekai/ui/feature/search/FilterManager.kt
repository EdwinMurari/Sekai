package com.edwin.sekai.ui.feature.search

import androidx.compose.runtime.Stable
import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams

@Stable
class FilterState(val searchParams: SearchParams) {

}

enum class FilterType {
    FORMAT,
    STATUS,
    SORT_BY,
    ORDER,
    GENRES,
    TAGS,
    MIN_SCORE,
    SEASON_YEAR,
    SEASON,
    IS_ADULT
}

// Extension function on SearchParams to get the filter value
fun SearchParams.getFilterValue(filterType: FilterType): Any? {
    return when (filterType) {
        FilterType.FORMAT -> format
        FilterType.STATUS -> status
        FilterType.SORT_BY -> sortBy
        FilterType.ORDER -> order
        FilterType.GENRES -> genres
        FilterType.TAGS -> tags
        FilterType.MIN_SCORE -> minScore
        FilterType.SEASON_YEAR -> seasonYear
        FilterType.SEASON -> season
        FilterType.IS_ADULT -> isAdult
    }
}

// Extension function on SearchParams to update the filter value
fun SearchParams.updateFilter(filterType: FilterType, newValue: Any?): SearchParams {
    return when (filterType) {
        FilterType.FORMAT -> copy(format = newValue as? MediaFormat)
        FilterType.STATUS -> copy(status = newValue as? MediaStatus)
        FilterType.SORT_BY -> copy(sortBy = newValue as? MediaSort)
        FilterType.ORDER -> copy(order = newValue as? Order ?: Order.DESCENDING)
        FilterType.GENRES -> copy(genres = newValue as? List<String>)
        FilterType.TAGS -> copy(tags = newValue as? List<String>)
        FilterType.MIN_SCORE -> copy(minScore = newValue as? Int)
        FilterType.SEASON_YEAR -> copy(seasonYear = newValue as? Int)
        FilterType.SEASON -> copy(season = newValue as? MediaSeason)
        FilterType.IS_ADULT -> copy(isAdult = newValue as? Boolean)
    }
}