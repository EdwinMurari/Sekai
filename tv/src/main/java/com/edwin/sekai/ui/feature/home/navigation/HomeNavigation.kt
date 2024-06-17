package com.edwin.sekai.ui.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavGraphBuilder.homeRoute(
    onMediaClick: (Int) -> Unit,
) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeRoute(onMediaClick = onMediaClick)
    }
}