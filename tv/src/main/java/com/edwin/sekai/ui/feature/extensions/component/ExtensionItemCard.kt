package com.edwin.sekai.ui.feature.extensions.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Text
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.component.DotSeparatedRow
import com.edwin.sekai.ui.designsystem.component.PreviewAbleSubComposeImage
import com.edwin.sekai.ui.designsystem.previewprovider.ExtensionPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel

object ExtensionItemCardDefault {
    const val CARD_WIDTH = 124
}

@Composable
fun ExtensionItemCard(
    modifier: Modifier,
    extension: ExtensionUiModel,
    onClickExtension: () -> Unit
) {
    StandardCardContainer(
        modifier = modifier,
        imageCard = { interactionSource ->
            Card(
                onClick = onClickExtension,
                interactionSource = interactionSource,
                modifier = Modifier
                    .width(ExtensionItemCardDefault.CARD_WIDTH.dp)
                    .aspectRatio(CardDefaults.SquareImageAspectRatio),
                shape = CardDefaults.shape(shape = CircleShape),
                border = CardDefaults.border(
                    focusedBorder = Border(
                        border = BorderStroke(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.border
                        ),
                        shape = CircleShape
                    )
                ),
            ) {
                PreviewAbleSubComposeImage(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = extension.iconUrl,
                    previewImage = painterResource(id = R.drawable.extension_placeholder),
                    contentDescription = "Extension icon",
                    contentScale = ContentScale.Crop
                )
            }
        },
        title = {
            Text(
                text = extension.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        },
        subtitle = {
            val contentList = mutableListOf<@Composable () -> Unit>().apply {
                when (extension) {
                    is ExtensionUiModel.Available -> {
                        add { Text(extension.language) }
                        if (extension.isNsfw) {
                            add { Text("18+") }
                        } else {
                            add { Text(extension.version) }
                        }
                    }

                    is ExtensionUiModel.Installed -> {
                        if (extension.hasUpdate) {
                            add { Text("Update") }
                        } else if (extension.isObsolete) {
                            add { Text("Obsolete") }
                        } else {
                            add { Text("Installed") }
                        }
                        if (extension.isNsfw) {
                            add { Text("18+") }
                        }
                    }
                }
            }

            DotSeparatedRow(
                modifier = modifier,
                contents = contentList.toTypedArray(),
                spacing = 4
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
fun ExtensionItemPreview(
    @PreviewParameter(ExtensionPreviewParameterProvider::class) extension: ExtensionUiModel
) {
    SekaiTheme {
        ExtensionItemCard(
            modifier = Modifier,
            extension = extension,
            onClickExtension = {}
        )
    }
}