package com.edwin.sekai.ui.feature.extensions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.extensions.ExtensionsRoute

const val EXTENSIONS_ROUTE = "extensions_route"

fun NavController.navigateToExtensions(navOptions: NavOptions? = null) =
    navigate(EXTENSIONS_ROUTE, navOptions)

fun NavGraphBuilder.extensionsRoute() {
    composable(
        route = EXTENSIONS_ROUTE
    ) {
        ExtensionsRoute()
    }
}