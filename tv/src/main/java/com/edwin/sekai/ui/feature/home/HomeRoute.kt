package com.edwin.sekai.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
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
import kotlin.enums.EnumEntries

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
    val tabs = TabNavOption.entries
    val contentFocusRequester = FocusRequester()

    Box(modifier = modifier.fillMaxSize()) {
        Content(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(contentFocusRequester),
            selectedTab = selectedTab,
            palettes = palettes,
            onMediaClick = onMediaClick
        )

        NavigationTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onPreviewKeyEvent {
                    when {
                        KeyEventType.KeyUp == it.type && Key.DirectionDown == it.key -> {
                            // TODO :: Temp fix, look into the cause
                            contentFocusRequester.requestFocus()
                            true
                        }

                        else -> false
                    }
                },
            tabs = tabs,
            selectedTab = selectedTab,
            onTabSelectionChange = onTabSelectionChange
        )
    }
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
                onMediaClick = onMediaClick,
            )
        }

        TabNavOption.Categories -> {
            CategoriesScreen(modifier = modifier)
        }

        TabNavOption.Search -> {
            SearchScreen(modifier = modifier)
        }

        TabNavOption.Extensions -> {
            ExtensionsScreen(modifier = modifier)
        }
    }
}

@Composable
@OptIn(ExperimentalTvMaterial3Api::class)
fun NavigationTopBar(
    tabs: EnumEntries<TabNavOption>,
    selectedTab: TabNavOption,
    onTabSelectionChange: (TabNavOption) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = tabs.indexOf(selectedTab),
        modifier = modifier
            .padding(top = 24.dp)
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
