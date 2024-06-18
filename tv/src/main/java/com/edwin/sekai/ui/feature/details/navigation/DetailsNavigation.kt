package com.edwin.sekai.ui.feature.details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.details.DetailsRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val MEDIA_ID_ARG = "mediaId"
const val MEDIA_ROUTE_BASE = "media_route"
const val MEDIA_ROUTE = "$MEDIA_ROUTE_BASE?$MEDIA_ID_ARG={$MEDIA_ID_ARG}"

class MediaDetailsArgs(val mediaId: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[MEDIA_ID_ARG]
            ), URL_CHARACTER_ENCODING
        ).toInt()
    )
}


fun NavHostController.navigateToDetails(
    mediaId: Int,
    navOptions: NavOptions? = null
) {
    val route = "${MEDIA_ROUTE_BASE}?${MEDIA_ID_ARG}=$mediaId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.detailsScreen(
    palettes: Map<String, Material3Palette>,
    onClickWatch: (Int, Int) -> Unit,
    onMediaClick: (Int) -> Unit
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
        DetailsRoute(
            palettes = palettes,
            onClickWatch = onClickWatch,
            onMediaClick = onMediaClick
        )
    }
}
