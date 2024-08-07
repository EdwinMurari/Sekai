package com.edwin.data.mapper

import com.edwin.data.model.MediaFormat
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.MediaSort
import com.edwin.data.model.MediaStatus
import com.edwin.data.model.Order
import com.edwin.data.model.SearchParams
import com.edwin.network.anilist.type.MediaFormat as AnilistMediaFormat
import com.edwin.network.anilist.type.MediaSeason as AnilistMediaSeason
import com.edwin.network.anilist.type.MediaSort as AnilistMediaSort
import com.edwin.network.anilist.type.MediaStatus as AnilistMediaStatus

val tvAnilistFormats = listOf(
    AnilistMediaFormat.TV,
    AnilistMediaFormat.TV_SHORT,
    AnilistMediaFormat.OVA,
    AnilistMediaFormat.ONA,
    AnilistMediaFormat.SPECIAL
)
val movieAnilistFormats = listOf(AnilistMediaFormat.MOVIE)

fun AnilistMediaFormat?.isTvSeries() = this in tvAnilistFormats
fun AnilistMediaFormat?.isMovie() = this in movieAnilistFormats

fun MediaSeason.asAnilistModel(): AnilistMediaSeason {
    return when (this) {
        MediaSeason.WINTER -> AnilistMediaSeason.WINTER
        MediaSeason.SPRING -> AnilistMediaSeason.SPRING
        MediaSeason.SUMMER -> AnilistMediaSeason.SUMMER
        MediaSeason.FALL -> AnilistMediaSeason.FALL
    }
}

fun MediaFormat.asAnilistModel() = when (this) {
    MediaFormat.TV -> tvAnilistFormats
    MediaFormat.MOVIE -> movieAnilistFormats
}

fun MediaStatus.asAnilistModel() = when (this) {
    MediaStatus.FINISHED -> AnilistMediaStatus.FINISHED
    MediaStatus.RELEASING -> AnilistMediaStatus.RELEASING
    MediaStatus.NOT_YET_RELEASED -> AnilistMediaStatus.NOT_YET_RELEASED
    MediaStatus.CANCELLED -> AnilistMediaStatus.CANCELLED
    MediaStatus.HIATUS -> AnilistMediaStatus.HIATUS
}

fun SearchParams.getAnilistMediaSort() = when (sortBy to order) {
    MediaSort.TITLE to Order.ASCENDING -> AnilistMediaSort.TITLE_ENGLISH
    MediaSort.TITLE to Order.DESCENDING -> AnilistMediaSort.TITLE_ENGLISH_DESC

    MediaSort.POPULARITY to Order.ASCENDING -> AnilistMediaSort.POPULARITY
    MediaSort.POPULARITY to Order.DESCENDING -> AnilistMediaSort.POPULARITY_DESC

    MediaSort.START_DATE to Order.ASCENDING -> AnilistMediaSort.START_DATE
    MediaSort.START_DATE to Order.DESCENDING -> AnilistMediaSort.START_DATE_DESC

    MediaSort.SCORE to Order.ASCENDING -> AnilistMediaSort.SCORE
    MediaSort.SCORE to Order.DESCENDING -> AnilistMediaSort.SCORE_DESC

    MediaSort.TRENDING to Order.ASCENDING -> AnilistMediaSort.TRENDING
    MediaSort.TRENDING to Order.DESCENDING -> AnilistMediaSort.TRENDING_DESC

    MediaSort.EPISODES to Order.ASCENDING -> AnilistMediaSort.EPISODES
    MediaSort.EPISODES to Order.DESCENDING -> AnilistMediaSort.EPISODES_DESC

    MediaSort.DURATION to Order.ASCENDING -> AnilistMediaSort.DURATION
    MediaSort.DURATION to Order.DESCENDING -> AnilistMediaSort.DURATION_DESC

    MediaSort.FORMAT to Order.ASCENDING -> AnilistMediaSort.FORMAT
    MediaSort.FORMAT to Order.DESCENDING -> AnilistMediaSort.FORMAT_DESC

    else -> null
}