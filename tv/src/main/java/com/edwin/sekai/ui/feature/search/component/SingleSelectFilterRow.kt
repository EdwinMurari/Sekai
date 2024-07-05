package com.edwin.sekai.ui.feature.search.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ListItem
import androidx.tv.material3.RadioButton
import androidx.tv.material3.Text
import com.edwin.sekai.ui.feature.search.model.FilterOption
import com.edwin.sekai.ui.utils.formatEnumName

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> SingleSelectFilterOptionContent(
    filter: FilterOption.SingleSelect<T>,
    contentPaddingValues: PaddingValues,
    onFilterOptionSelected: (FilterOption.SingleSelect<T>) -> Unit,
) {
    BackHandler { onFilterOptionSelected(filter) }

    TvLazyColumn(contentPadding = contentPaddingValues) {
        items(filter.options, key = { it.toString() }) { option ->
            ListItem(
                headlineContent = { Text(formatEnumName(option.toString())) },
                trailingContent = {
                    RadioButton(
                        selected = filter.selectedValue == option,
                        enabled = true,
                        onClick = null
                    )
                },
                onClick = { onFilterOptionSelected(filter.copy(selectedValue = option)) },
                selected = false
            )
        }
    }
}