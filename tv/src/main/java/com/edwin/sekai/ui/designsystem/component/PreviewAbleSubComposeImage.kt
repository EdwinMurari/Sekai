package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.edwin.sekai.R

@Composable
fun PreviewAbleSubComposeImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    previewImage: Painter
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = previewImage,
            contentDescription = "image",
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            loading = { Loading() },
            error = { painterResource(id = R.drawable.placeholder) },
            modifier = modifier
        )
    }
}