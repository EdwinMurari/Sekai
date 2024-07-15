package com.edwin.sekai.ui.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.search.SearchRoute

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchRoute(
    contentPaddingValues: PaddingValues,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    composable(
        route = SEARCH_ROUTE
    ) {
        SearchRoute(
            contentPaddingValues = contentPaddingValues,
            onMediaClick = onMediaClick,
            palettes = palettes,
            isTopBarVisible = isTopBarVisible,
            updateTopBarVisibility = updateTopBarVisibility
        )
    }
}