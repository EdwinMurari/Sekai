package com.edwin.sekai.ui.feature.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text

@Composable
fun BrowseRoute(
    onMediaClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    Text("Browse screen TODO")
}

@Composable
fun BrowseScreen(
    uiState: BrowseViewModel.UiState
) {}