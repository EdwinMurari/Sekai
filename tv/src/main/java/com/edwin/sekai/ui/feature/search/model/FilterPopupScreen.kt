package com.edwin.sekai.ui.feature.search.model

sealed interface FilterPopupScreen {
    data object FilterList : FilterPopupScreen
    data class FilterOptions(val filter: FilterOption<*>) : FilterPopupScreen
}