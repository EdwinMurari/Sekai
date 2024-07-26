package com.edwin.sekai.ui.feature.settings.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.settings.SettingsRoute

const val SETTINGS_ROUTE = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsRoute(
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    composable(route = SETTINGS_ROUTE) {
        SettingsRoute(
            contentPaddingValues = contentPaddingValues,
            isTopBarVisible = isTopBarVisible,
            updateTopBarVisibility = updateTopBarVisibility
        )
    }
}

