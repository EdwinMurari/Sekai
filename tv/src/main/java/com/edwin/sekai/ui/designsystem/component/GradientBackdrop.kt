package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

@Composable
fun BoxScope.GradientBackdrop(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    Box(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .align(Alignment.TopCenter)
    ) {
        PreviewAbleSubComposeImage(
            imageUrl = imageUrl,
            contentDescription = "Gradient backdrop $imageUrl",
            contentScale = ContentScale.Crop,
            previewImage = painterResource(id = R.drawable.naruto_banner),
            modifier = Modifier.fillMaxSize()
        )

        val colorStops = arrayOf(
            0.0f to Color.Transparent,
            0.7f to MaterialTheme.colorScheme.surface
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops = colorStops))
        )
    }
}

@TvPreview
@Composable
fun PreviewGradientBackdrop() {
    SekaiTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            GradientBackdrop(imageUrl = null)
        }
    }
}