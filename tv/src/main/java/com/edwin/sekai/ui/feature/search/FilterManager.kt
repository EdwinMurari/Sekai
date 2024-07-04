package com.edwin.sekai.ui.feature.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Nature
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.ui.graphics.vector.ImageVector

enum class FilterType(val title: String, val icon: ImageVector) {
    FORMAT(title = "Format", icon = Icons.Outlined.Movie),
    STATUS(title = "Status", icon = Icons.Default.PlayCircleOutline),
    SORT_BY(title = "Sort by", icon = Icons.AutoMirrored.Filled.Sort),
    ORDER(title = "Order", icon = Icons.Default.SwapVert),
    GENRES(title = "Genres", icon = Icons.Default.Category),
    MIN_SCORE(title = "Min Score", icon = Icons.Default.Star),
    SEASON_YEAR(title = "Season Year", icon = Icons.Default.CalendarToday),
    SEASON(title = "Season", icon = Icons.Outlined.Nature),
    IS_ADULT(title = "Is Adult", icon = Icons.Outlined.Lock)
}