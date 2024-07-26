package com.edwin.sekai.ui.feature.extensions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.ProvideTextStyle
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.Loading
import com.edwin.sekai.ui.designsystem.component.PreviewAbleSubComposeImage
import com.edwin.sekai.ui.designsystem.component.SomethingWentWrong

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
        updateTopBarVisibility = updateTopBarVisibility
    )
}

@Composable
fun ExtensionsScreen(
    uiState: ExtensionsUiState,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
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
                updateTopBarVisibility = updateTopBarVisibility
            )
        }
    }
}

private const val CARD_HEIGHT = 234
private const val CARD_WIDTH = 156

@Composable
private fun ExtensionsContent(
    uiState: ExtensionsUiState.Success,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    isTopBarVisible: Boolean = true,
    updateTopBarVisibility: (Boolean) -> Unit,
) {
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
        columns = TvGridCells.Adaptive(minSize = 156.dp)
    ) {
        items(uiState.extensions) { extension ->

            val interactionSource = remember { MutableInteractionSource() }

            val isFocused by interactionSource.collectIsFocusedAsState()

            val borderColor by animateColorAsState(
                targetValue = if (isFocused) MaterialTheme.colorScheme.border
                else Color.Transparent,
                label = "Media Card Border Color"
            )

            Surface(
                modifier = modifier,
                interactionSource = interactionSource,
                shape = ClickableSurfaceDefaults.shape(
                    shape = RoundedCornerShape(0.dp),
                    focusedShape = RoundedCornerShape(0.dp),
                    pressedShape = RoundedCornerShape(0.dp),
                ),
                colors = ClickableSurfaceDefaults.colors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = Color.Transparent,
                    focusedContentColor = MaterialTheme.colorScheme.onSurface,
                    pressedContainerColor = Color.Transparent,
                    pressedContentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = { },
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.width(IntrinsicSize.Min)
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    color = borderColor,
                                    shape = MaterialTheme.shapes.large,
                                    width = 3.dp
                                )
                                .size(width = CARD_WIDTH.dp, height = CARD_HEIGHT.dp)
                                .clip(MaterialTheme.shapes.large)
                        ) {
                            PreviewAbleSubComposeImage(
                                imageUrl = extension.iconUrl,
                                previewImage = painterResource(id = R.drawable.naruto),
                                contentDescription = "${extension.title} cover image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }

                        Column(
                            modifier = modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ProvideTextStyle(MaterialTheme.typography.labelSmall) {
                                Text(extension.version)
                            }

                            ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                                Text(text = extension.title)
                            }
                        }
                    }
                }
            )
        }
    }
}