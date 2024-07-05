package com.edwin.sekai.ui.utils

fun formatEnumName(enumName: String): String {
    return enumName.split("_")
        .joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
}