package com.edwin.sekai.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.loadMaterial3Palettes
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.browse.BrowseRoute
import com.edwin.sekai.ui.feature.categories.CategoriesScreen
import com.edwin.sekai.ui.feature.extensions.ExtensionsScreen
import com.edwin.sekai.ui.feature.search.SearchScreen

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

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(
    selectedTab: TabNavOption,
    palettes: Map<String, Material3Palette>,
    onTabSelectionChange: (TabNavOption) -> Unit,
    onMediaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = TabNavOption.entries

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = tabs.indexOf(selectedTab),
            modifier = Modifier
                .padding(top = 24.dp)
                .focusRestorer()
                .padding(8.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                key(index) {
                    Tab(
                        modifier = Modifier.padding(8.dp),
                        selected = tab == selectedTab,
                        onFocus = { onTabSelectionChange(tab) },
                    ) {
                        Text(text = tab.label)
                    }
                }
            }
        }

        val contentModifier = Modifier.weight(1f)

        when (selectedTab) {
            TabNavOption.Home -> {
                BrowseRoute(
                    modifier = contentModifier,
                    palettes = palettes,
                    onMediaClick = onMediaClick,
                )
            }

            TabNavOption.Categories -> {
                CategoriesScreen(modifier = contentModifier)
            }

            TabNavOption.Search -> {
                SearchScreen(modifier = contentModifier)
            }

            TabNavOption.Extensions -> {
                ExtensionsScreen(modifier = contentModifier)
            }
        }
    }
}

enum class TabNavOption(val label: String) {
    Home("Home"),
    Categories("Categories"),
    Search("Search"),
    Extensions("Extensions")
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
