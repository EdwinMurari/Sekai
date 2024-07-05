package com.edwin.sekai.ui.feature.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Text
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.browse.BrowseRoute
import com.edwin.sekai.ui.feature.categories.CategoriesScreen
import com.edwin.sekai.ui.feature.extensions.ExtensionsScreen
import com.edwin.sekai.ui.feature.search.SearchRoute

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

    ModalNavDrawer(selectedTab, onTabSelectionChange, modifier = modifier, content = { adawd ->
        Content(
            modifier = Modifier.fillMaxSize(),
            selectedTab = selectedTab,
            palettes = palettes,
            onMediaClick = onMediaClick
        )
    })
}

@Composable
private fun Content(
    selectedTab: TabNavOption,
    modifier: Modifier,
    palettes: Map<String, Material3Palette>,
    onMediaClick: (Int) -> Unit
) {
    when (selectedTab) {
        TabNavOption.Home -> {
            BrowseRoute(
                modifier = modifier,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }

        TabNavOption.Categories -> {
            CategoriesScreen(modifier = modifier)
        }

        TabNavOption.Search -> {
            SearchRoute(
                modifier = modifier,
                palettes = palettes,
                onMediaClick = onMediaClick
            )
        }

        TabNavOption.Extensions -> {
            ExtensionsScreen(modifier = modifier)
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ModalNavDrawer(
    currentNavOption: TabNavOption,
    onCurrentScreenUpdate: (TabNavOption) -> Unit,
    content: @Composable (TabNavOption) -> Unit,
    modifier: Modifier = Modifier
) {

    val tabs = TabNavOption.entries

    NavigationDrawer(
        modifier = modifier,
        drawerContent = { drawerValue ->
            Column(
                Modifier
                    .background(Color.Transparent)
                    .fillMaxHeight()
                    .selectableGroup(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                tabs.forEachIndexed { index, tab ->
                    key(index) {
                        NavigationDrawerItem(
                            selected = currentNavOption == tab,
                            onClick = {
                                onCurrentScreenUpdate(tab)
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = null,
                                )
                            }
                        ) {
                            Text(stringResource(tab.labelResId))
                        }
                    }
                }
            }
        },
        content = { content(currentNavOption) }
    )
}

enum class TabNavOption(val icon: ImageVector, @StringRes val labelResId: Int) {
    Home(Icons.Rounded.Home, R.string.nav_option_home_label),
    Categories(Icons.Rounded.Menu, R.string.nav_option_categories_label),
    Search(Icons.Rounded.Search, R.string.nav_option_search_label),
    Extensions(Icons.Rounded.AddCircleOutline, R.string.nav_option_extensions_label)
}

@TvPreview
@Composable
fun PreviewHome() {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(TabNavOption.Home) }
    val context = LocalContext.current
    val palettes = loadMaterial3Palettes(context)

    SekaiTheme {
        HomeScreen(
            selectedTab = selectedTab,
            onTabSelectionChange = setSelectedTab,
            onMediaClick = {},
            palettes = palettes
        )
    }
}
