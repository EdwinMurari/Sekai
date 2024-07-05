package com.edwin.sekai.ui.feature.search.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Checkbox
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import com.edwin.sekai.ui.feature.search.model.FilterOption

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> MultiSelectFilterOptionContent(
    filter: FilterOption.MultiSelect<T>,
    contentPaddingValues: PaddingValues,
    onFilterOptionSelected: (FilterOption.MultiSelect<T>) -> Unit,
) {
    val currentSelection = remember {
        mutableStateListOf<T>().apply {
            filter.selectedValue?.let { addAll(it) }
        }
    }

    BackHandler { onFilterOptionSelected(filter.copy(selectedValue = currentSelection.toList())) }

    TvLazyColumn(contentPadding = contentPaddingValues) {
        items(filter.options) { option ->
            ListItem(
                headlineContent = { Text(option.toString()) },
                onClick = {
                    if (currentSelection.contains(option)) {
                        currentSelection.remove(option)
                    } else {
                        currentSelection.add(option)
                    }
                },
                trailingContent = {
                    Checkbox(
                        checked = currentSelection.contains(option),
                        enabled = true,
                        onCheckedChange = null
                    )
                },
                selected = false
            )
        }
    }
}