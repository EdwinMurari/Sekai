package com.edwin.sekai.ui.feature.settings


import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.itemsIndexed
import androidx.tv.material3.Icon
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.UserPreference
import com.edwin.sekai.R
import com.edwin.sekai.ui.feature.settings.section.ExtensionRepositoriesSection

@Composable
fun SettingsRoute(
    contentPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        contentPaddingValues = contentPaddingValues,
        userPreference = userPreferences,
        isTopBarVisible = isTopBarVisible,
        onClickAddRepository = viewModel::onClickAddRepository,
        onClickRemoveRepository = viewModel::onClickRemoveRepository,
        updateTopBarVisibility = updateTopBarVisibility,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    userPreference: UserPreference,
    isTopBarVisible: Boolean = true,
    @FloatRange(from = 0.0, to = 1.0) sidebarWidthFraction: Float = 0.32f,
    onClickAddRepository: (String) -> Unit,
    onClickRemoveRepository: (String) -> Unit,
    updateTopBarVisibility: (Boolean) -> Unit
) {
    val settingsNavController = rememberNavController()

    val backStack by settingsNavController.currentBackStackEntryAsState()
    val currentDestination =
        remember(backStack?.destination?.route) { backStack?.destination?.route }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isLeftColumnFocused by remember { mutableStateOf(false) }
    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Row(
        modifier = modifier.padding(
            start = contentPaddingValues.calculateStartPadding(layoutDirection),
            end = contentPaddingValues.calculateEndPadding(layoutDirection)
        )
    ) {
        TvLazyColumn(
            modifier = Modifier
                .fillMaxWidth(fraction = sidebarWidthFraction)
                .fillMaxHeight()
                .onFocusChanged {
                    isLeftColumnFocused = it.hasFocus
                }
                .focusRestorer()
                .focusGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = contentPaddingValues.calculateTopPadding(),
                bottom = contentPaddingValues.calculateBottomPadding()
            )
        ) {
            itemsIndexed(
                items = SettingScreens.entries,
                key = { index, _ -> index }
            ) { index, settingScreen ->
                ListItem(
                    leadingContent = {
                        Icon(
                            imageVector = settingScreen.icon,
                            contentDescription = stringResource(settingScreen.titleResId)
                        )
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(settingScreen.titleResId)
                        )
                    },
                    selected = currentDestination == settingScreen.name,
                    onClick = { focusManager.moveFocus(FocusDirection.Right) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (index == 0) Modifier.focusRequester(focusRequester)
                            else Modifier
                        )
                        .onFocusChanged {
                            if (it.isFocused && currentDestination != settingScreen.name) {
                                settingsNavController.navigate(settingScreen.route) {
                                    currentDestination?.let { nnCurrentDestination ->
                                        popUpTo(nnCurrentDestination) { inclusive = true }
                                    }
                                    launchSingleTop = true
                                }
                            }
                        },
                    scale = ListItemDefaults.scale(focusedScale = 1f),
                    colors = ListItemDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                        selectedContainerColor = MaterialTheme.colorScheme.inverseSurface
                            .copy(alpha = 0.4f),
                        selectedContentColor = MaterialTheme.colorScheme.surface,
                    ),
                    shape = ListItemDefaults.shape(shape = MaterialTheme.shapes.extraSmall)
                )
            }
        }

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .onPreviewKeyEvent {
                    if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
                        // Using 'while' because AccountsScreen has a grid that has multiple items
                        // in a row for which we would need to press D-Pad Left multiple times
                        while (!isLeftColumnFocused) {
                            focusManager.moveFocus(FocusDirection.Left)
                        }
                        return@onPreviewKeyEvent true
                    }
                    false
                },
            navController = settingsNavController,
            startDestination = SettingScreens.ExtensionRepositories.route,
            builder = {
                composable(SettingScreens.ExtensionRepositories.route) {
                    ExtensionRepositoriesSection(
                        contentPaddingValues = PaddingValues(
                            top = contentPaddingValues.calculateTopPadding(),
                            bottom = contentPaddingValues.calculateBottomPadding(),
                            start = 20.dp,
                            end = 20.dp
                        ),
                        repositories = userPreference.repositoryUrls,
                        onClickAddRepository = onClickAddRepository,
                        onClickRemoveRepository = onClickRemoveRepository
                    )
                }
            }
        )
    }
}

enum class SettingScreens(
    val icon: ImageVector,
    @StringRes val titleResId: Int,
) {
    ExtensionRepositories(
        icon = Icons.Default.Download,
        titleResId = R.string.extension_repositories_settings_screen_title
    );

    val route = name
}
