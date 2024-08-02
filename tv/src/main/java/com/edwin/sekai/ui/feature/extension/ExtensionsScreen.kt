package com.edwin.sekai.ui.feature.extension

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.Material3Palette
import com.edwin.sekai.ui.designsystem.component.MediaCard
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong
import com.edwin.sekai.ui.feature.extensions.component.ExtensionItemCardDefault

@Composable
fun ExtensionRoute(
    modifier: Modifier = Modifier,
    viewModel: ExtensionViewModel = hiltViewModel(),
    palettes: Map<String, Material3Palette>,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExtensionScreen(
        modifier = modifier,
        uiState = uiState,
        palettes = palettes
    )
}

@Composable
fun ExtensionScreen(
    modifier: Modifier = Modifier,
    uiState: ExtensionUiState,
    palettes: Map<String, Material3Palette>,
) {
    ExtensionScreenContent(
        modifier = modifier,
        uiState = uiState,
        palettes = palettes
    )
}

@Composable
private fun ExtensionScreenContent(
    uiState: ExtensionUiState,
    modifier: Modifier = Modifier,
    palettes: Map<String, Material3Palette>,
) {
    when (uiState) {
        ExtensionUiState.Error -> {
            SomethingWentWrong(
                modifier = modifier,
                text = "Something went wrong",
                buttonText = "Retry",
                onAction = {}
            )
        }

        ExtensionUiState.Loading -> {
            Loading(modifier = modifier)
        }

        is ExtensionUiState.Success -> {
            ExtensionsContent(
                uiState = uiState,
                palettes = palettes,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ExtensionsContent(
    uiState: ExtensionUiState.Success,
    palettes: Map<String, Material3Palette>,
    modifier: Modifier = Modifier
) {
    TvLazyVerticalGrid(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        columns = TvGridCells.Adaptive(minSize = ExtensionItemCardDefault.CARD_WIDTH.dp)
    ) {
        items(uiState.media, key = { it.id }) { media ->
            MediaCard(
                media = media,
                palettes = palettes,
                onClick = { }
            )
        }
    }
}