package com.edwin.sekai.ui.feature.extensions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong
import com.edwin.sekai.ui.feature.extensions.component.ExtensionItemCard
import com.edwin.sekai.ui.feature.extensions.component.ExtensionItemCardDefault
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import com.edwin.sekai.ui.utils.launchRequestPackageInstallsPermission
import com.edwin.sekai.ui.utils.rememberRequestPackageInstallsPermissionState

@Composable
fun ExtensionsRoute(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
    viewModel: ExtensionsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExtensionsScreen(
        modifier = modifier,
        uiState = uiState,
        contentPaddingValues = contentPaddingValues,
        isTopBarVisible = isTopBarVisible,
        updateTopBarVisibility = updateTopBarVisibility,
        onExtensionClick = viewModel::onExtensionClick
    )
}

@Composable
fun ExtensionsScreen(
    uiState: ExtensionsUiState,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
    onExtensionClick: (ExtensionUiModel) -> Unit
) {
    when (uiState) {
        ExtensionsUiState.Error -> {
            SomethingWentWrong(
                modifier = modifier,
                text = "Something went wrong",
                buttonText = "Retry",
                onAction = {}
            )
        }

        ExtensionsUiState.Loading -> {
            Loading(modifier = modifier)
        }

        is ExtensionsUiState.Success -> {
            ExtensionsContent(
                uiState = uiState,
                modifier = modifier,
                contentPaddingValues = contentPaddingValues,
                isTopBarVisible = isTopBarVisible,
                updateTopBarVisibility = updateTopBarVisibility,
                onExtensionClick = onExtensionClick
            )
        }
    }
}

@Composable
private fun ExtensionsContent(
    uiState: ExtensionsUiState.Success,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
    onExtensionClick: (ExtensionUiModel) -> Unit
) {
    val context = LocalContext.current
    val installPermissionGranted = rememberRequestPackageInstallsPermissionState()

    val lazyGridState = rememberTvLazyGridState()

    val shouldShowTopBar by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == 0 &&
                    lazyGridState.firstVisibleItemScrollOffset < 300
        }
    }

    LaunchedEffect(shouldShowTopBar) {
        updateTopBarVisibility(shouldShowTopBar)
    }

    LaunchedEffect(isTopBarVisible) {
        if (isTopBarVisible) lazyGridState.animateScrollToItem(0)
    }

    TvLazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        contentPadding = contentPaddingValues,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = TvGridCells.Adaptive(minSize = ExtensionItemCardDefault.CARD_WIDTH.dp)
    ) {
        items(uiState.extensions) { extension ->
            ExtensionItemCard(
                modifier = modifier,
                extension = extension,
                onClickExtension = {
                    if (installPermissionGranted) {
                        onExtensionClick(extension)
                    } else {
                        context.launchRequestPackageInstallsPermission()
                    }
                }
            )
        }
    }
}