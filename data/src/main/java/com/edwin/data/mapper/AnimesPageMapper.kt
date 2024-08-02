package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.network.extensions.aniyomi.model.AnimesPage
import kotlin.random.Random

fun AnimesPage.asExternalModel(): List<Media> {
    return animes?.map {
        Media.TvSeries(
            id = Random.nextInt(),
            title = it.title,
            description = it.description,
            coverImage = it.thumbnail_url,
            bannerImage = it.thumbnail_url,
            genres = it.getGenres(),
            averageColorHex = null,
            rawAverageScore = null,
            popularity = null,
            startDate = null,
            episodesCount = null
        )
    } ?: emptyList()
}