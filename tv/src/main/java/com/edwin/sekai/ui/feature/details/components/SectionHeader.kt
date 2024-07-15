package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyListScope
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

fun TvLazyListScope.sectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    item(contentType = "SectionHeader", key = "${text}SectionHeader") {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(top = 24.dp)
        )
    }
}