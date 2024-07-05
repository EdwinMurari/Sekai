package com.edwin.sekai.ui.feature.search.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.edwin.sekai.ui.feature.search.model.FilterOption
import com.edwin.sekai.ui.feature.search.model.FilterPopupScreen

@Composable
fun rememberFilterPopupState(
    initialFilters: List<FilterOption<*>>
): FilterPopupState {
    return remember(initialFilters) { FilterPopupState(initialFilters) }
}

class FilterPopupState(initialFilters: List<FilterOption<*>>) {
    val filters = mutableStateListOf<FilterOption<*>>().apply { addAll(initialFilters) }

    var currentScreen by mutableStateOf<FilterPopupScreen>(FilterPopupScreen.FilterList)
        private set

    fun onFilterSelected(filter: FilterOption<*>) {
        currentScreen = FilterPopupScreen.FilterOptions(filter)
    }

    fun updateFilter(updatedFilter: FilterOption<*>) {
        val index = filters.indexOfFirst { it.filterType == updatedFilter.filterType }
        if (index != -1) {
            filters[index] = updatedFilter
        }
        currentScreen = FilterPopupScreen.FilterList
    }
}