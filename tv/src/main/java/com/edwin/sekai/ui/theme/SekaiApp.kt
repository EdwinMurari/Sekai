package com.edwin.sekai.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Text
import com.edwin.sekai.ui.feature.browse.BrowseRoute
import com.edwin.sekai.ui.feature.details.navigation.detailsScreen
import com.edwin.sekai.ui.feature.details.navigation.navigateToDetails
import com.edwin.sekai.ui.feature.extensions.ExtensionsScreen
import com.edwin.sekai.ui.feature.search.SearchScreen
import com.edwin.sekai.ui.feature.stream.StreamRoute
import com.edwin.sekai.ui.feature.stream.navigation.navigateToStream
import com.edwin.sekai.ui.theme.ScreenArgumentKeys.DETAILS_ID_ARG
import com.edwin.sekai.ui.theme.ScreenArgumentKeys.EPISODE_NUMBER_ARG

@Composable
fun SekaiApp() {
    val navController = rememberNavController()

    var currentNavOption by remember { mutableStateOf<NavOption>(NavOption.Home) }

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            ModalNavDrawer(
                currentNavOption = currentNavOption,
                onCurrentScreenUpdate = { currentNavOption = it }
            ) { screen ->
                when (screen) {
                    NavOption.Home -> {
                        BrowseRoute(navController::navigateToDetails)
                    }

                    NavOption.Search -> {
                        SearchScreen()
                    }

                    NavOption.Extensions -> {
                        ExtensionsScreen()
                    }
                }
            }
        }

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

@Composable
fun ModalNavDrawer(
    currentNavOption: NavOption,
    onCurrentScreenUpdate: (NavOption) -> Unit,
    content: @Composable (NavOption) -> Unit
) {
    val items = listOf(
        NavOption.Home,
        NavOption.Search,
        NavOption.Extensions
    )

    NavigationDrawer(
        drawerContent = { drawerValue ->
            Column(
                Modifier
                    .background(Color.Transparent)
                    .fillMaxHeight()
                    .selectableGroup(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items.forEach { screen ->
                    NavigationDrawerItem(
                        selected = currentNavOption == screen,
                        onClick = {
                            onCurrentScreenUpdate(screen)
                        },
                        leadingContent = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                            )
                        }
                    ) {
                        Text(screen.label)
                    }
                }
            }
        },
        content = { content(currentNavOption) }
    )
}

object ScreenArgumentKeys {

    const val DETAILS_ID_ARG = "id"
    const val EPISODE_NUMBER_ARG = "episodeNum"
}

sealed class NavOption(val icon: ImageVector, val label: String) {

    data object Home : NavOption(Icons.Default.Home, "Home")
    data object Search : NavOption(Icons.Default.Search, "Search")
    data object Extensions : NavOption(Icons.AutoMirrored.Filled.List, "Extensions")
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