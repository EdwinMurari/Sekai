package com.edwin.sekai.ui.feature.home.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.edwin.sekai.R

enum class TabNavOption(val icon: ImageVector, @StringRes val labelResId: Int) {
    Home(Icons.Rounded.Home, R.string.nav_option_home_label),
    Categories(Icons.Rounded.Menu, R.string.nav_option_categories_label),
    Search(Icons.Rounded.Search, R.string.nav_option_search_label),
    Extensions(Icons.Rounded.AddCircleOutline, R.string.nav_option_extensions_label)
}