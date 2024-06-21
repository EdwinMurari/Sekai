package com.edwin.network.kitsu.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KitsuEpisodeResponse(
    @SerialName("data") val data: List<KitsuEpisodeData>?,
    // ... other fields if needed
)

@Serializable
data class KitsuEpisodeData(
    @SerialName("id") val id: String,
    @SerialName("attributes") val attributes: KitsuEpisodeAttributes?
)

@Serializable
data class KitsuEpisodeAttributes(
    @SerialName("canonicalTitle") val canonicalTitle: String?,
    @SerialName("seasonNumber") val seasonNumber: Int?,
    @SerialName("number") val episodeNumber: Int?,
    @SerialName("synopsis") val synopsis: String?,
    @SerialName("airdate") val airdate: String?, // Consider using a Date format
    // ... other relevant fields ...
)