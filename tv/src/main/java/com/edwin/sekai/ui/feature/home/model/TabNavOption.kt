package com.edwin.sekai.ui.feature.home.model

import androidx.annotation.StringRes
import com.edwin.sekai.R

enum class TabNavOption(@StringRes val labelResId: Int) {
    Home(R.string.nav_option_home_label),
    Categories(R.string.nav_option_categories_label),
    Search(R.string.nav_option_search_label),
    Extensions(R.string.nav_option_extensions_label)
}