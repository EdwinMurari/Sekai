package com.edwin.data.model

data class NetworkMedia(
    val id: Int,
    val title: Title,
    val coverImageUrl: String,
    val averageScore: Double,
    val popularity: Int
)

data class Title(
    val english: String,
    val romaji: String
)