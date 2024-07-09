package com.edwin.sekai.ui.feature.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@Composable
fun ExtensionsRoute(
    modifier: Modifier = Modifier
) {
    ExtensionsScreen(modifier = modifier)
}

@Composable
fun ExtensionsScreen(modifier: Modifier = Modifier) {
    Text("Extension screen TODO")
}