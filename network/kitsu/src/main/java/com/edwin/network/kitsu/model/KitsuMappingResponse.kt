package com.edwin.network.kitsu.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KitsuMappingResponse(
    @SerialName("data") val data: List<KitsuMappingData>? = null
)

@Serializable
data class KitsuMappingData(
    @SerialName("id") val id: String,
    @SerialName("attributes") val attributes: KitsuMappingAttributes? = null
)

@Serializable
data class KitsuMappingAttributes(
    @SerialName("externalSite") val externalSite: String? = null,
    @SerialName("externalId") val externalId: String? = null
)