package com.edwin.sekai.ui.feature.details

import androidx.compose.runtime.Composable
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text

@Composable
fun DetailsRoute(onWatchEpisodeClick: (String, Int) -> Unit) {
    DetailsScreen()
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DetailsScreen() {
    Text("Details screen TODO")
}