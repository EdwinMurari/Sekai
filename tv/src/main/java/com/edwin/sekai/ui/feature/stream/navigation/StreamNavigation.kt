package com.edwin.sekai.ui.feature.stream.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.stream.StreamRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val MEDIA_ID_ARG = "mediaId"
const val EPISODE_NUMBER_ARG = "episodeNumber"
const val STREAM_ROUTE_BASE = "stream_route"
const val STREAM_ROUTE =
    "$STREAM_ROUTE_BASE?$MEDIA_ID_ARG={$MEDIA_ID_ARG}&$EPISODE_NUMBER_ARG={$EPISODE_NUMBER_ARG}"

class StreamArgs(val mediaId: Int, val episodeNumber: Int) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[MEDIA_ID_ARG]
            ), URL_CHARACTER_ENCODING
        ).toInt(),
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[EPISODE_NUMBER_ARG]
            ), URL_CHARACTER_ENCODING
        ).toInt()
    )
}

fun NavHostController.navigateToStream(
    mediaId: Int,
    episodeNumber: Int,
    navOptions: NavOptions? = null
) {
    val route = "$STREAM_ROUTE_BASE?${MEDIA_ID_ARG}=$mediaId&${EPISODE_NUMBER_ARG}=$episodeNumber"
    navigate(route, navOptions)
}

fun NavGraphBuilder.streamRoute(palettes: Map<String, Material3Palette>) {
    composable(
        route = STREAM_ROUTE,
        arguments = listOf(
            navArgument(MEDIA_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
            navArgument(EPISODE_NUMBER_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            }
        ),
    ) {
        StreamRoute()
    }
}