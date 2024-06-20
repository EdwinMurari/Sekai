package com.edwin.sekai.ui.feature.browse.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.browse.BrowseRoute

const val BROWSE_ROUTE = "browse_route"

fun NavController.navigateToBrowse(navOptions: NavOptions) = navigate(BROWSE_ROUTE, navOptions)

fun NavGraphBuilder.browseScreen(
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    showTopBar: (Boolean) -> Unit
) {
    composable(route = BROWSE_ROUTE) {
        BrowseRoute(
            onMediaClick = onMediaClick,
            palettes = palettes,
            showTopBar = showTopBar
        )
    }
}
