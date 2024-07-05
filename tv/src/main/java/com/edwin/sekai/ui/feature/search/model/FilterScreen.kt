package com.edwin.sekai.ui.feature.search.model

sealed interface FilterScreen {
    data object FilterList : FilterScreen
    data class SingleSelectOptions<T>(val filter: FilterOption.SingleSelect<T>) : FilterScreen
    data class MultiSelectOptions<T>(val filter: FilterOption.MultiSelect<T>) : FilterScreen
}