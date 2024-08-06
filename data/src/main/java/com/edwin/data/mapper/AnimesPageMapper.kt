package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.network.extensions.model.MediaPage
import kotlin.random.Random

fun MediaPage.asExternalModel(): List<Media> {
    return media.map {
        Media.TvSeries(
            id = Random.nextInt(),
            title = it.title,
            description = it.description,
            coverImage = it.thumbnailUrl,
            bannerImage = it.thumbnailUrl,
            genres = it.genre?.let { listOf(it) },
            averageColorHex = null,
            rawAverageScore = null,
            popularity = null,
            startDate = null,
            episodesCount = null
        )
    }
}