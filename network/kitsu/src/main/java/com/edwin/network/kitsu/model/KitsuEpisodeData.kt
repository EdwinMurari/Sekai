package com.edwin.network.kitsu.model

import kotlinx.serialization.SerialName

data class KitsuEpisodeResponse(
    @SerialName("data") val data: List<KitsuEpisodeData>?,
    // ... other fields if needed
)

data class KitsuEpisodeData(
    @SerialName("id") val id: String,
    @SerialName("attributes") val attributes: KitsuEpisodeAttributes?
)

data class KitsuEpisodeAttributes(
    @SerialName("canonicalTitle") val canonicalTitle: String?,
    @SerialName("seasonNumber") val seasonNumber: Int?,
    @SerialName("number") val episodeNumber: Int?,
    @SerialName("synopsis") val synopsis: String?,
    @SerialName("airdate") val airdate: String?, // Consider using a Date format
    // ... other relevant fields ...
)