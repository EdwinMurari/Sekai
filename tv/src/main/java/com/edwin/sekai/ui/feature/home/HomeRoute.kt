package com.edwin.sekai.ui.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.TopBar
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.browse.navigation.BROWSE_ROUTE
import com.edwin.sekai.ui.feature.browse.navigation.browseRoute
import com.edwin.sekai.ui.feature.browse.navigation.navigateToBrowse
import com.edwin.sekai.ui.feature.categories.navigation.categoriesRoute
import com.edwin.sekai.ui.feature.extensions.navigation.extensionsRoute
import com.edwin.sekai.ui.feature.home.model.TabNavOption
import com.edwin.sekai.ui.feature.search.navigation.searchRoute

@Composable
fun HomeRoute(
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    HomeScreen(
        palettes = palettes,
        onMediaClick = onMediaClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = TabNavOption.entries
    val focusRequesters = remember { options.associateWith { FocusRequester() } }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTabOption by remember {
        derivedStateOf {
            options.find { it.route == navBackStackEntry?.destination?.route }
        }
    }

    var isTopBarVisible by remember { mutableStateOf(true) }

    val contentPadding = PaddingValues(vertical = 40.dp, horizontal = 58.dp)
    val focusManager = LocalFocusManager.current

    BackHandler {
        // 1. On user's first back press, bring focus to the current selected tab, if TopBar is not
        //    visible, first make it visible, then focus the selected tab
        // 2. On second back press, bring focus back to the first displayed tab
        // 3. On third back press, exit the app

        when {
            !isTopBarVisible -> {
                isTopBarVisible = true
                focusRequesters[currentTabOption]?.requestFocus()
            }

            currentTabOption != TabNavOption.Home -> {
                val topLevelNavOption = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                navController.navigateToBrowse(topLevelNavOption)
            }

            else -> {
                navController.navigateUp()
            }
        }
    }

    // We do not want to focus the TopBar everytime we come back from another screen e.g.
    // MovieDetails, CategoryMovieList or VideoPlayer screen
    var wasTopBarFocusRequestedBefore by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!wasTopBarFocusRequestedBefore) {
            focusRequesters[currentTabOption]?.requestFocus()
            wasTopBarFocusRequestedBefore = true
        }
    }

    Column(modifier = modifier) {
        AnimatedVisibility(isTopBarVisible) {
            TopBar(
                modifier = Modifier
                    .padding(horizontal = 58.dp)
                    .padding(top = 32.dp),
                selectedTabIndex = options.indexOf(currentTabOption)
            ) {
                options.forEachIndexed { index, option ->
                    TextItem(
                        key = index,
                        labelResId = option.labelResId,
                        focusRequester = focusRequesters[option]!!,
                        selected = currentTabOption == option,
                        focusManager = focusManager,
                        onSelectOption = {
                            navController.navigate(option.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

        Body(
            contentPaddingValues = contentPadding,
            palettes = palettes,
            onMediaClick = onMediaClick,
            updateTopBarVisibility = { isTopBarVisible = it },
            isTopBarVisible = isTopBarVisible,
            navController = navController,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Body(
    contentPaddingValues: PaddingValues,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BROWSE_ROUTE,
    ) {
        browseRoute(
            palettes = palettes,
            isTopBarVisible = isTopBarVisible,
            contentPaddingValues = contentPaddingValues,
            onMediaClick = onMediaClick,
            updateTopBarVisibility = updateTopBarVisibility
        )

        categoriesRoute()

        searchRoute(
            contentPaddingValues = contentPaddingValues,
            isTopBarVisible = isTopBarVisible,
            onMediaClick = onMediaClick,
            palettes = palettes,
            updateTopBarVisibility = updateTopBarVisibility
        )

        extensionsRoute()
    }
}

@TvPreview
@Composable
fun HomeScreenPreview() {
    SekaiTheme {
        val context = LocalContext.current
        val palettes = loadMaterial3Palettes(context)

        HomeScreen(
            palettes = palettes,
            onMediaClick = {},
            modifier = Modifier
        )
    }
}