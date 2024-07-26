package com.edwin.sekai.ui.feature.settings.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import com.edwin.sekai.ui.designsystem.component.SearchTextField

@Composable
fun ExtensionRepositoriesSection(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    repositories: List<String>,
    onClickAddRepository: (String) -> Unit,
    onClickRemoveRepository: (String) -> Unit
) {
    TvLazyColumn(
        modifier = modifier,
        contentPadding = contentPaddingValues
    ) {
        item {
            val (searchQuery, updateSearchQuery) = remember { mutableStateOf("") }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchTextField(
                    modifier = Modifier.weight(1f),
                    searchQuery = searchQuery,
                    onSearchQueryChange = updateSearchQuery,
                    onSearch = onClickAddRepository
                )
            }
        }

        items(repositories) { repository ->
            ListItem(
                headlineContent = {
                    Text(repository)
                },
                onClick = { },
                trailingContent = {
                    IconButton(onClick = { onClickRemoveRepository(repository) }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Delete repository"
                        )
                    }
                },
                selected = false
            )
        }
    }
}