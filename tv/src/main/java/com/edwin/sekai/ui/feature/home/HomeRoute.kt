package com.edwin.sekai.ui.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
import com.edwin.sekai.ui.feature.categories.navigation.navigateToCategories
import com.edwin.sekai.ui.feature.extensions.navigation.extensionsRoute
import com.edwin.sekai.ui.feature.extensions.navigation.navigateToExtensions
import com.edwin.sekai.ui.feature.home.model.TabNavOption
import com.edwin.sekai.ui.feature.search.navigation.navigateToSearch
import com.edwin.sekai.ui.feature.search.navigation.searchRoute

val ParentPadding = PaddingValues(vertical = 16.dp, horizontal = 58.dp)

@Composable
fun rememberChildPadding(direction: LayoutDirection = LocalLayoutDirection.current): PaddingValues {
    return remember {
        PaddingValues(
            start = ParentPadding.calculateStartPadding(direction) + 8.dp,
            top = ParentPadding.calculateTopPadding(),
            end = ParentPadding.calculateEndPadding(direction) + 8.dp,
            bottom = ParentPadding.calculateBottomPadding()
        )
    }
}

@Composable
fun HomeRoute(
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>
) {
    HomeScreen(
        selectedTab = viewModel.selectedTab,
        palettes = palettes,
        onTabSelectionChange = viewModel::setTab,
        onMediaClick = onMediaClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    selectedTab: TabNavOption,
    palettes: Map<String, Material3Palette>,
    onTabSelectionChange: (TabNavOption) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    var isTopBarVisible by remember { mutableStateOf(true) }
    var isTopBarFocused by remember { mutableStateOf(false) }

    val options = TabNavOption.entries
    val focusRequesters = remember { List(options.size) { FocusRequester() } }

    val currentTopBarSelectedTabIndex by remember(selectedTab) {
        derivedStateOf {
            TabNavOption.entries.indexOf(selectedTab)
        }
    }

    if (currentTopBarSelectedTabIndex != 0) {
        // 1. On user's first back press, bring focus to the current selected tab, if TopBar is not
        //    visible, first make it visible, then focus the selected tab
        // 2. On second back press, bring focus back to the first displayed tab
        // 3. On third back press, exit the app
        BackHandler {
            when {
                !isTopBarVisible -> {
                    isTopBarVisible = true
                    focusRequesters[currentTopBarSelectedTabIndex].requestFocus()
                }

                !isTopBarFocused -> {
                    focusRequesters[currentTopBarSelectedTabIndex].requestFocus()
                }

                else -> {
                    focusRequesters[1].requestFocus()
                }
            }
        }
    }

    // We do not want to focus the TopBar everytime we come back from another screen e.g.
    // MovieDetails, CategoryMovieList or VideoPlayer screen
    var wasTopBarFocusRequestedBefore by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!wasTopBarFocusRequestedBefore) {
            focusRequesters[currentTopBarSelectedTabIndex].requestFocus()
            wasTopBarFocusRequestedBefore = true
        }
    }

    Column(modifier = modifier) {
        AnimatedVisibility(isTopBarVisible) {
            TopBar(
                modifier = Modifier
                    .onFocusChanged { isTopBarFocused = it.hasFocus }
                    .padding(
                        horizontal = ParentPadding.calculateStartPadding(
                            LocalLayoutDirection.current
                        ) + 8.dp
                    )
                    .padding(
                        top = ParentPadding.calculateTopPadding(),
                        bottom = ParentPadding.calculateBottomPadding()
                    ),
                options = options,
                focusRequesters = focusRequesters,
                selectedTabIndex = currentTopBarSelectedTabIndex,
            ) { screen ->
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

                when (screen) {
                    TabNavOption.Home -> navController.navigateToBrowse(topLevelNavOption)
                    TabNavOption.Categories -> navController.navigateToCategories(topLevelNavOption)
                    TabNavOption.Search -> navController.navigateToSearch(topLevelNavOption)
                    TabNavOption.Extensions -> navController.navigateToExtensions(topLevelNavOption)
                }

                onTabSelectionChange(screen)
            }
        }

        Body(
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
            onMediaClick = onMediaClick,
            updateTopBarVisibility = updateTopBarVisibility
        )

        categoriesRoute()

        searchRoute(onMediaClick, palettes)

        extensionsRoute()
    }
}

@TvPreview
@Composable
fun HomeScreenPreview() {
    SekaiTheme {
        val context = LocalContext.current
        val palettes = loadMaterial3Palettes(context)
        val (selectedTab, setTab) = remember { mutableStateOf(TabNavOption.Home) }

        HomeScreen(
            selectedTab = selectedTab,
            palettes = palettes,
            onMediaClick = {},
            onTabSelectionChange = setTab,
            modifier = Modifier
        )
    }
}