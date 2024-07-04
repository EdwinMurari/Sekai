package com.edwin.network.jikan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JikanEpisodesResponse(
    @SerialName("pagination") val pagination: JikanPagination? = null,
    @SerialName("data") val data: List<JikanEpisodeData>? = null
)

@Serializable
data class JikanPagination(
    @SerialName("last_visible_page") val lastVisiblePage: Int? = null,
    @SerialName("has_next_page") val hasNextPage: Boolean
)

@Serializable
data class JikanEpisodeData(
    @SerialName("mal_id") val malId: Int,
    @SerialName("url") val url: String?,
    @SerialName("title") val title: String?,
    @SerialName("title_japanese") val titleJapanese: String? = null,
    @SerialName("title_romanji") val titleRomanji: String? = null,
    @SerialName("aired") val aired: String? = null,
    @SerialName("score") val score: Double? = null,
    @SerialName("filler") val filler: Boolean,
    @SerialName("recap") val recap: Boolean,
    @SerialName("forum_url") val forumUrl: String? = null
)