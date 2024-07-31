package com.edwin.sekai.ui.feature.extensions.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.extensions.ExtensionsRoute
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel

const val EXTENSIONS_ROUTE = "extensions_route"

fun NavController.navigateToExtensions(navOptions: NavOptions? = null) =
    navigate(EXTENSIONS_ROUTE, navOptions)

fun NavGraphBuilder.extensionsRoute(
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
    onClickBrowse: (ExtensionUiModel.Installed) -> Unit
) {
    composable(
        route = EXTENSIONS_ROUTE
    ) {
        ExtensionsRoute(
            contentPaddingValues = contentPaddingValues,
            isTopBarVisible = isTopBarVisible,
            updateTopBarVisibility = updateTopBarVisibility,
            onClickBrowse = onClickBrowse
        )
    }
}