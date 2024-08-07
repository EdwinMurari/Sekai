package com.edwin.data.mapper

import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams
import com.edwin.network.extensions.model.MediaFormat as ExtensionMediaFormat
import com.edwin.network.extensions.model.MediaSeason as ExtensionMediaSeason
import com.edwin.network.extensions.model.MediaSort as ExtensionMediaSort
import com.edwin.network.extensions.model.MediaStatus as ExtensionMediaStatus

val tvExtensionFormats = listOf(
    ExtensionMediaFormat.TV,
    ExtensionMediaFormat.TV_SHORT,
    ExtensionMediaFormat.OVA,
    ExtensionMediaFormat.ONA,
    ExtensionMediaFormat.SPECIAL
)
val movieExtensionFormats = listOf(ExtensionMediaFormat.MOVIE)

fun ExtensionMediaFormat?.isTvSeries() = this in tvExtensionFormats
fun ExtensionMediaFormat?.isMovie() = this in movieExtensionFormats

fun MediaSeason.asExtensionModel(): ExtensionMediaSeason {
    return when (this) {
        MediaSeason.WINTER -> ExtensionMediaSeason.WINTER
        MediaSeason.SPRING -> ExtensionMediaSeason.SPRING
        MediaSeason.SUMMER -> ExtensionMediaSeason.SUMMER
        MediaSeason.FALL -> ExtensionMediaSeason.FALL
    }
}

fun MediaFormat.asExtensionModel() = when (this) {
    MediaFormat.TV -> tvExtensionFormats
    MediaFormat.MOVIE -> movieExtensionFormats
}

fun MediaStatus.asExtensionModel() = when (this) {
    MediaStatus.FINISHED -> ExtensionMediaStatus.FINISHED
    MediaStatus.RELEASING -> ExtensionMediaStatus.RELEASING
    MediaStatus.NOT_YET_RELEASED -> ExtensionMediaStatus.NOT_YET_RELEASED
    MediaStatus.CANCELLED -> ExtensionMediaStatus.CANCELLED
    MediaStatus.HIATUS -> ExtensionMediaStatus.HIATUS
}

fun SearchParams.getExtensionMediaSort() = when (sortBy to order) {
    MediaSort.TITLE to Order.ASCENDING -> ExtensionMediaSort.TITLE_ENGLISH
    MediaSort.TITLE to Order.DESCENDING -> ExtensionMediaSort.TITLE_ENGLISH_DESC

    MediaSort.POPULARITY to Order.ASCENDING -> ExtensionMediaSort.POPULARITY
    MediaSort.POPULARITY to Order.DESCENDING -> ExtensionMediaSort.POPULARITY_DESC

    MediaSort.START_DATE to Order.ASCENDING -> ExtensionMediaSort.START_DATE
    MediaSort.START_DATE to Order.DESCENDING -> ExtensionMediaSort.START_DATE_DESC

    MediaSort.SCORE to Order.ASCENDING -> ExtensionMediaSort.SCORE
    MediaSort.SCORE to Order.DESCENDING -> ExtensionMediaSort.SCORE_DESC

    MediaSort.TRENDING to Order.ASCENDING -> ExtensionMediaSort.TRENDING
    MediaSort.TRENDING to Order.DESCENDING -> ExtensionMediaSort.TRENDING_DESC

    MediaSort.EPISODES to Order.ASCENDING -> ExtensionMediaSort.EPISODES
    MediaSort.EPISODES to Order.DESCENDING -> ExtensionMediaSort.EPISODES_DESC

    MediaSort.DURATION to Order.ASCENDING -> ExtensionMediaSort.DURATION
    MediaSort.DURATION to Order.DESCENDING -> ExtensionMediaSort.DURATION_DESC

    MediaSort.FORMAT to Order.ASCENDING -> ExtensionMediaSort.FORMAT
    MediaSort.FORMAT to Order.DESCENDING -> ExtensionMediaSort.FORMAT_DESC

    else -> null
}