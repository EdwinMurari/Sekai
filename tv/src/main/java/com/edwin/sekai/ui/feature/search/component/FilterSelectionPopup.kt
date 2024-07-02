package com.edwin.sekai.ui.feature.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ListItem
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams
import com.edwin.sekai.ui.feature.search.FilterType
import com.edwin.sekai.ui.feature.search.getFilterValue

@Composable
@OptIn(ExperimentalTvMaterial3Api::class)
fun BoxScope.FilterSelectionPopup(
    modifier: Modifier = Modifier,
    filterType: FilterType,
    selectedValue: Any?,
    onFilterParamChanged: (FilterType, Any?) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .align(Alignment.CenterEnd)
            .padding(24.dp)
            .width(280.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(horizontal = 20.dp)
        ) {
            FilterOptionsScreen(
                filterType = filterType,
                selectedValue = selectedValue,
                onOptionSelected = { newValue ->
                    onFilterParamChanged(filterType, newValue)
                }
            )
        }
    }

    // Request focus when the popup is opened
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun FilterOptionsScreen(
    filterType: FilterType,
    selectedValue: Any?,
    onOptionSelected: (Any?) -> Unit
) {
    val options = when (filterType) {
        FilterType.FORMAT -> MediaFormat.entries.toTypedArray()
        FilterType.STATUS -> MediaStatus.entries.toTypedArray()
        FilterType.SORT_BY -> MediaSort.entries.toTypedArray()
        FilterType.ORDER -> Order.entries.toTypedArray()
        FilterType.GENRES -> TODO()
        FilterType.TAGS -> TODO()
        FilterType.MIN_SCORE -> TODO()
        FilterType.SEASON_YEAR -> TODO()
        FilterType.SEASON -> TODO()
        FilterType.IS_ADULT -> TODO()
    }

    Column {
        ListHeader(text = filterType.name)
        options.forEach { option ->
            OptionRow(
                option = option,
                isSelected = option == selectedValue
            ) {
                onOptionSelected(option)
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun OptionRow(
    option: Any,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    } else {
        Color.Transparent
    }

    ListItem(
        modifier = Modifier
            .background(backgroundColor)
            .padding(vertical = 4.dp),
        headlineContent = { Text(option.toString()) },
        onClick = onClick,
        selected = false
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ListHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .padding(vertical = 8.dp),
        text = text,
        style = MaterialTheme.typography.titleMedium
    )
}