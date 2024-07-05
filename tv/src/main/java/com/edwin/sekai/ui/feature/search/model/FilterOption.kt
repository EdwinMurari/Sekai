package com.edwin.sekai.ui.feature.search.model

sealed interface FilterOption<T> {
    val filterType: FilterType
    val selectedValue: T?

    data class SingleSelect<T>(
        override val filterType: FilterType,
        override val selectedValue: T?,
        val options: List<T>
    ) : FilterOption<T>

    data class MultiSelect<T>(
        override val filterType: FilterType,
        override val selectedValue: List<T>?,
        val options: List<T>
    ) : FilterOption<List<T>>
}