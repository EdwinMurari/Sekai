package com.edwin.sekai.ui.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.search.SearchRoute

const val SEARCH_ROUTE = "search_route"

fun NavGraphBuilder.searchRoute(
    onMediaClick: (Int) -> Unit,
    palettes: Map<String, Material3Palette>,
) {
    composable(
        route = SEARCH_ROUTE
    ) {
        SearchRoute(
            onMediaClick = onMediaClick,
            palettes = palettes
        )
    }
}