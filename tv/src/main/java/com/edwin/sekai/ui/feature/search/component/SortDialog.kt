package com.edwin.sekai.ui.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.RadioButton
import androidx.tv.material3.Text
import com.edwin.data.model.MediaSort
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

// --- Sort Screen Composable ---
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SortScreen(
    initialSortBy: MediaSort,
    onSortChange: (MediaSort) -> Unit,
    onDismissRequest: () -> Unit  // Function to close the sort screen
) {
    var selectedSort by remember { mutableStateOf(initialSortBy) }

    // Modal/Overlay implementation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sort by",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "Reset",
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        selectedSort = MediaSort.POPULARITY
                        onSortChange(selectedSort)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Sort Options List
            LazyColumn {
                items(MediaSort.entries.toTypedArray()) { sortOption ->
                    SortOptionRow(
                        sortOption = sortOption,
                        isSelected = selectedSort == sortOption
                    ) { selected ->
                        selectedSort = selected
                        onSortChange(selected)
                    }
                }
            }
        }
    }
}

// --- Reusable Composable for Sort Options ---
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SortOptionRow(
    sortOption: MediaSort,
    isSelected: Boolean,
    onSortSelected: (MediaSort) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSortSelected(sortOption) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sortOption.name,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = { onSortSelected(sortOption) } // Notify when selected
        )
    }
}

@TvPreview
@Composable
fun SortScreenPreview() {
    SekaiTheme {
        SortScreen(
            initialSortBy = MediaSort.POPULARITY,
            onSortChange = {},
            onDismissRequest = {}
        )
    }
}