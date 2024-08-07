package com.edwin.sekai.ui.feature.extension.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import com.edwin.sekai.ui.feature.search.ExtensionBrowseRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8

private val URL_CHARACTER_ENCODING = UTF_8.name()

const val EXTENSION_PKG_NAME_ARG = "extension_pkg_name"
const val EXTENSION_ROUTE_BASE = "extension_route"
const val EXTENSION_ROUTE =
    "$EXTENSION_ROUTE_BASE?$EXTENSION_PKG_NAME_ARG={$EXTENSION_PKG_NAME_ARG}"

class ExtensionArgs(val pkgName: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[EXTENSION_PKG_NAME_ARG]
            ), URL_CHARACTER_ENCODING
        )
    )
}

fun NavHostController.navigateToExtension(
    extensionUiModel: ExtensionUiModel,
    navOptions: NavOptions? = null
) {
    val route = "$EXTENSION_ROUTE_BASE?$EXTENSION_PKG_NAME_ARG=${extensionUiModel.pkgName}"
    navigate(route, navOptions)
}

fun NavGraphBuilder.extensionRoute(
    palettes: Map<String, Material3Palette>,
) {
    composable(
        route = EXTENSION_ROUTE,
        arguments = listOf(
            navArgument(EXTENSION_PKG_NAME_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        ExtensionBrowseRoute(
            onMediaClick = {},
            palettes = palettes,
        )
    }
}
