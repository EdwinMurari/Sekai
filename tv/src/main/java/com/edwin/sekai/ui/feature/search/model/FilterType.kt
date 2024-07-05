package com.edwin.sekai.ui.feature.search.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Nature
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.ui.graphics.vector.ImageVector

enum class FilterType(val title: String, val icon: ImageVector) {
    SORT_BY(title = "Sort by", icon = Icons.AutoMirrored.Rounded.Sort),
    ORDER(title = "Order", icon = Icons.Rounded.SwapVert),
    FORMAT(title = "Format", icon = Icons.Rounded.Movie),
    STATUS(title = "Status", icon = Icons.Rounded.PlayCircleOutline),
    GENRES(title = "Genres", icon = Icons.Rounded.Category),
    MIN_SCORE(title = "Min Score", icon = Icons.Rounded.Star),
    SEASON_YEAR(title = "Season Year", icon = Icons.Rounded.CalendarToday),
    SEASON(title = "Season", icon = Icons.Rounded.Nature),
    IS_ADULT(title = "Adult Content", icon = Icons.Rounded.Lock)
}