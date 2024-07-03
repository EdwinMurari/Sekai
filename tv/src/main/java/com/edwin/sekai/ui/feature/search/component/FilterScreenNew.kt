package com.edwin.sekai.ui.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.edwin.data.model.SearchParams
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme


// --- Filter Screen Composable ---
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FilterScreen(
    initialSearchParams: SearchParams,
    onApplyFilters: (SearchParams) -> Unit,
    onDismissRequest: () -> Unit // Function to close the filter screen
) {
    var searchParams by remember { mutableStateOf(initialSearchParams) }

    // Modal/Overlay implementation (consider using ModalBottomSheet for a true overlay)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // "Filters" title and "Clear" button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Clear",
                    color = Color.Gray, // You can adjust color as needed
                    modifier = Modifier.clickable {
                        // Handle the Clear button click (e.g., reset searchParams)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Filter Categories
            LazyColumn {
                item {
                    FilterCategoryRow(
                        icon = Icons.Default.ArrowForward, // Replace with appropriate icon
                        title = "Format",
                        selectedValue = searchParams.format?.name ?: "",
                        onClick = {
                            // Handle navigation to Length filter selection screen
                        }
                    )
                }
                // ... Repeat for other filter categories
                item {
                    FilterCategoryRow(
                        icon = Icons.Default.ArrowForward, // Replace with appropriate icon
                        title = "Status",
                        selectedValue = searchParams.status?.name ?: "",
                        onClick = {
                            // Handle navigation to Instructor filter selection screen
                        }
                    )
                }
                // ... Add more filter categories here
            }

            // Apply Button
            Button(
                onClick = { onApplyFilters(searchParams) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Apply Filters")
            }
        }
    }
}

// --- Reusable Row Composable for Filter Categories ---
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FilterCategoryRow(
    icon: ImageVector,
    title: String,
    selectedValue: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray // Adjust color as needed
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title)
        Spacer(Modifier.weight(1f))
        Text(
            text = selectedValue,
            color = Color.Gray
        )
    }
}

@TvPreview
@Composable
fun FilterScreenPreview() {
    SekaiTheme {
        FilterScreen(
            initialSearchParams = SearchParams(1, 20),
            onApplyFilters = {},
            onDismissRequest = {}
        )
    }
}