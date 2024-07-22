package com.edwin.sekai.ui.feature.home.model

import androidx.annotation.StringRes
import com.edwin.sekai.R
import com.edwin.sekai.ui.feature.browse.navigation.BROWSE_ROUTE
import com.edwin.sekai.ui.feature.categories.navigation.CATEGORIES_ROUTE
import com.edwin.sekai.ui.feature.extensions.navigation.EXTENSIONS_ROUTE
import com.edwin.sekai.ui.feature.search.navigation.SEARCH_ROUTE

enum class TabNavOption(@StringRes val labelResId: Int, val route: String) {
    Home(R.string.nav_option_home_label, BROWSE_ROUTE),
    Categories(R.string.nav_option_categories_label, CATEGORIES_ROUTE),
    Search(R.string.nav_option_search_label, SEARCH_ROUTE),
    Extensions(R.string.nav_option_extensions_label, EXTENSIONS_ROUTE)
}