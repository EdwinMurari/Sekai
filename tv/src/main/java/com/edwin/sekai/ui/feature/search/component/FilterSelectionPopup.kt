package com.edwin.sekai.ui.feature.search.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.OutlinedButton
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.edwin.data.model.SearchParams

@Composable
@OptIn(ExperimentalTvMaterial3Api::class)
fun BoxScope.FilterSelectionPopup(
    modifier: Modifier = Modifier,
    searchParams: SearchParams,
    onFilterParamsChanged: (SearchParams) -> Unit
) {
    Surface(
        modifier = modifier
            .align(Alignment.CenterEnd)
            .padding(24.dp)
            .width(280.dp)
            .fillMaxHeight()
    ) {
        FilterList(searchParams = searchParams, onFilterParamsChanged = onFilterParamsChanged)
    }
}

@Composable
fun FilterList(
    searchParams: SearchParams,
    onFilterParamsChanged: (SearchParams) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier) {
        ListHeader(
            modifier = Modifier
                .focusable(false)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp, bottom = 4.dp),
            text = "Filters"
        )

        Column(
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(top = 4.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FilterButton(
                icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                label = "Format",
                onClick = { /* Handle format filter */ }
            )
            FilterButton(
                icon = Icons.Default.Circle,
                label = "Status",
                onClick = { /* Handle status filter */ }
            )
            FilterButton(
                icon = Icons.AutoMirrored.Filled.Sort,
                label = "Sort By",
                onClick = { /* Handle sort filter */ }
            )
            FilterButton(
                icon = Icons.Default.Category,
                label = "Genres",
                onClick = { /* Handle genre filter */ }
            )
            FilterButton(
                icon = Icons.Default.Tag,
                label = "Tags",
                onClick = { /* Handle tag filter */ }
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FilterButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = label)
            Spacer(Modifier.width(8.dp))
            Text(text = label)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun ListHeader(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .padding(bottom = 16.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
}