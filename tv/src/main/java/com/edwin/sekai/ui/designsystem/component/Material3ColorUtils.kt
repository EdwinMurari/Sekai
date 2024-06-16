package com.edwin.sekai.ui.designsystem.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.json.JSONArray
import kotlin.math.sqrt

@Composable
fun loadMaterial3Palettes(context: Context): Map<String, Material3Palette> {
    val palettes = remember { mutableStateMapOf<String, Material3Palette>() }

    LaunchedEffect(Unit) {
        val jsonString =
            context.assets.open("material3colors.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val key = jsonObject.keys().next()
            val paletteObject = jsonObject.getJSONObject(key)
            val palette = Material3Palette(
                name = key,
                primary = paletteObject.optString("primary"),
                onPrimary = paletteObject.optString("onPrimary"),
                secondary = paletteObject.optString("secondary"),
                onSecondary = paletteObject.optString("onSecondary"),
                tertiary = paletteObject.optString("tertiary"),
                onTertiary = paletteObject.optString("onTertiary"),
                error = paletteObject.optString("error"),
                onError = paletteObject.optString("onError"),
                background = paletteObject.optString("background"),
                onBackground = paletteObject.optString("onBackground"),
                surface = paletteObject.optString("surface"),
                onSurface = paletteObject.optString("onSurface")
            )
            palettes[key] = palette
        }
    }

    return palettes
}

fun colorDistance(color1: Color, color2: Color): Double {
    val redDiff = color1.red - color2.red
    val greenDiff = color1.green - color2.green
    val blueDiff = color1.blue - color2.blue

    return sqrt((redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff).toDouble())
}

fun findClosestPalette(
    targetColor: Color,
    palettes: Map<String, Material3Palette>
): Material3Palette? {
    var closestPalette: Material3Palette? = null
    var minDistance = Double.MAX_VALUE

    for ((_, palette) in palettes) {
        palette.primary?.let { primaryColor ->
            val distance =
                colorDistance(targetColor, Color(android.graphics.Color.parseColor(primaryColor)))
            if (distance < minDistance) {
                minDistance = distance
                closestPalette = palette
            }
        }
    }

    return closestPalette
}

// Luminance calculation function (from previous examples)
fun Color.luminance(): Float {
    val color = this.toArgb()
    val red = android.graphics.Color.red(color) / 255f
    val green = android.graphics.Color.green(color) / 255f
    val blue = android.graphics.Color.blue(color) / 255f

    return if (red <= 0.03928f) red / 12.92f else Math.pow(
        ((red + 0.055f) / 1.055f).toDouble(),
        2.4
    ).toFloat() +
            (if (green <= 0.03928f) green / 12.92f else Math.pow(
                ((green + 0.055f) / 1.055f).toDouble(),
                2.4
            ).toFloat()) +
            (if (blue <= 0.03928f) blue / 12.92f else Math.pow(
                ((blue + 0.055f) / 1.055f).toDouble(),
                2.4
            ).toFloat())
}