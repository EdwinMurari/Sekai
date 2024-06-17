package com.edwin.sekai.ui.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.edwin.sekai.ui.designsystem.theme.ScreenArgumentKeys.DETAILS_ID_ARG
import com.edwin.sekai.ui.designsystem.theme.ScreenArgumentKeys.EPISODE_NUMBER_ARG
import com.edwin.sekai.ui.feature.details.navigation.detailsScreen
import com.edwin.sekai.ui.feature.details.navigation.navigateToDetails
import com.edwin.sekai.ui.feature.home.navigation.HOME_ROUTE
import com.edwin.sekai.ui.feature.home.navigation.homeRoute
import com.edwin.sekai.ui.feature.stream.StreamRoute
import com.edwin.sekai.ui.feature.stream.navigation.navigateToStream

@Composable
fun SekaiApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        homeRoute(onMediaClick = navController::navigateToDetails)

        detailsScreen(onWatchEpisodeClick = navController::navigateToStream)

        composable(
            route = Screen.WatchEpisode().route,
            arguments = listOf(
                navArgument(DETAILS_ID_ARG) { type = NavType.StringType },
                navArgument(EPISODE_NUMBER_ARG) { type = NavType.StringType }
            )
        ) {
            StreamRoute()
        }
    }
}

object ScreenArgumentKeys {

    const val DETAILS_ID_ARG = "id"
    const val EPISODE_NUMBER_ARG = "episodeNum"
}

sealed class Screen(
    private val path: String,
    private val arg1: String? = null,
    private val arg2: String? = null
) {

    val route: String
        get() {
            return "$path${arg1?.let { "/$it" } ?: ""}${arg2?.let { "/$it" } ?: ""}"
        }

    data object Home : Screen("/home")
    class Details(arg: String? = "{${DETAILS_ID_ARG}") : Screen("/movie", arg)
    class WatchEpisode(
        mediaId: String? = "{$DETAILS_ID_ARG}",
        episodeNum: String? = "{$EPISODE_NUMBER_ARG}"
    ) : Screen("/watchEpisode", mediaId, episodeNum)
}