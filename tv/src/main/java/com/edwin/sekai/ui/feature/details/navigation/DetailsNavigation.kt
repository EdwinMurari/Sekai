package com.edwin.sekai.ui.feature.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edwin.sekai.ui.feature.details.DetailsRoute

const val MEDIA_ID_ARG = "mediaId"
const val MEDIA_ROUTE_BASE = "media_route"
const val MEDIA_ROUTE = "$MEDIA_ROUTE_BASE?$MEDIA_ID_ARG={$MEDIA_ID_ARG}"

fun NavHostController.navigateToDetails(
    mediaId: Int,
    navOptions: NavOptions? = null
) {
    val route = "${MEDIA_ROUTE_BASE}?${MEDIA_ID_ARG}=$mediaId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.detailsScreen(
    onWatchEpisodeClick: (String, Int) -> Unit,
) {
    composable(
        route = MEDIA_ROUTE,
        arguments = listOf(
            navArgument(MEDIA_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        DetailsRoute(onWatchEpisodeClick = onWatchEpisodeClick)
    }
}
