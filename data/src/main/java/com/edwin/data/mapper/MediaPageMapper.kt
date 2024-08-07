package com.edwin.data.mapper

import com.edwin.data.model.Media
import com.edwin.data.model.MediaPageResponse
import com.edwin.data.model.PageInfo
import com.edwin.network.extensions.model.MediaPage
import kotlin.random.Random
import com.edwin.network.extensions.model.Media as NetworkMedia
import com.edwin.network.extensions.model.PageInfo as NetworkPageInfo

fun MediaPage.asExternalModel() = MediaPageResponse(
    pageInfo = pageInfo.asExternalModel(),
    media = media.map { it.asExternalModel() }
)

private fun NetworkPageInfo.asExternalModel() = PageInfo(
    hasNextPage = hasNextPage
)

private fun NetworkMedia.asExternalModel() = Media.TvSeries(
    id = Random.nextInt(),
    title = title,
    description = description,
    coverImage = thumbnailUrl,
    bannerImage = thumbnailUrl,
    genres = genres,
    averageColorHex = null,
    rawAverageScore = null,
    popularity = null,
    startDate = null,
    episodesCount = null
)