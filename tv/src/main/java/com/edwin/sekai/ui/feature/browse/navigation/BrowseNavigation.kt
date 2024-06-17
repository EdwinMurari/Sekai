package com.edwin.sekai.ui.feature.browse.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.browse.BrowseRoute

const val BROWSE_ROUTE = "browse_route"

fun NavController.navigateToBrowse(navOptions: NavOptions) = navigate(BROWSE_ROUTE, navOptions)

fun NavGraphBuilder.browseScreen(
    onMediaClick: (Int) -> Unit
) {
    composable(route = BROWSE_ROUTE) {
        BrowseRoute(onMediaClick)
    }
}
