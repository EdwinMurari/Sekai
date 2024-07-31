package com.edwin.sekai.ui.feature.extensions.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import com.edwin.sekai.ui.designsystem.component.RightOverlayDialog
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun InstalledExtensionPopup(
    selectedExtension: ExtensionUiModel.Installed?,
    onClickUpdate: (ExtensionUiModel.Installed) -> Unit,
    onClickBrowse: (ExtensionUiModel.Installed) -> Unit,
    onClickUninstall: (ExtensionUiModel.Installed) -> Unit,
    onDismissRequest: () -> Unit
) {
    val showDialog by remember(selectedExtension) {
        derivedStateOf { selectedExtension != null }
    }

    RightOverlayDialog(
        showDialog = showDialog,
        onDismissRequest = onDismissRequest,
        title = { modifier ->
            Text(
                modifier = modifier,
                text = selectedExtension?.title ?: "Extension"
            )
        },
        titleActionButton = {
            IconButton(onClick = onDismissRequest) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
        },
        content = { paddingValues ->
            TvLazyColumn(contentPadding = paddingValues) {
                if (selectedExtension?.hasUpdate == true) {
                    item {
                        ListItem(
                            headlineContent = { Text(text = "Update") },
                            onClick = { onClickUpdate(selectedExtension) },
                            selected = false
                        )
                    }
                }

                item {
                    ListItem(
                        headlineContent = { Text(text = "Browse") },
                        onClick = { onClickBrowse(selectedExtension!!) },
                        selected = false
                    )
                }

                item {
                    ListItem(
                        headlineContent = { Text(text = "Uninstall") },
                        onClick = { onClickUninstall(selectedExtension!!) },
                        selected = false
                    )
                }
            }
        }
    )
}