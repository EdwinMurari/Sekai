package com.edwin.sekai.ui.feature.categories

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.Text

@Composable
fun CategoriesRoute(
    modifier: Modifier = Modifier
) {
    CategoriesScreen(modifier = modifier)
}

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Text("Categories Screen")
}