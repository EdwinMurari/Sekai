package com.edwin.sekai.ui.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.feature.details.navigation.detailsRoute
import com.edwin.sekai.ui.feature.details.navigation.navigateToDetails
import com.edwin.sekai.ui.feature.home.navigation.HOME_ROUTE
import com.edwin.sekai.ui.feature.home.navigation.homeRoute
import com.edwin.sekai.ui.feature.stream.navigation.navigateToStream
import com.edwin.sekai.ui.feature.stream.navigation.streamRoute

@Composable
fun SekaiApp() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        homeRoute(
            onMediaClick = navController::navigateToDetails,
            palettes = palettes
        )

        detailsRoute(
            palettes = palettes,
            onClickWatch = navController::navigateToStream,
            onMediaClick = navController::navigateToDetails
        )

        streamRoute()
    }
}