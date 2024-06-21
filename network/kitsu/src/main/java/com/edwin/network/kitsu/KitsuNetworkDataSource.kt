package com.edwin.network.kitsu

import com.edwin.network.kitsu.model.KitsuEpisodeResponse

interface KitsuNetworkDataSource {

    suspend fun getEpisodeForAnime(malId: Int): KitsuEpisodeResponse
}