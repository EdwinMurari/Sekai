package com.edwin.network.jikan

import com.edwin.network.jikan.model.JikanEpisodesResponse

interface JikanNetworkDataSource {

    suspend fun getEpisodeForAnime(malId: Int): JikanEpisodesResponse
}