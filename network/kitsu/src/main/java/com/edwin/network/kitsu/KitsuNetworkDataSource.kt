package com.edwin.network.kitsu

import com.edwin.network.kitsu.model.KitsuEpisodeResponse

interface KitsuNetworkDataSource {

    suspend fun getEpisodeForAnime(animeId: String): KitsuEpisodeResponse

    suspend fun getKitsuIdFromMalId(malId: Int, externalSite: String): String?
}