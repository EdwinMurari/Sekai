package com.edwin.network.jikan.retrofit

import com.edwin.network.jikan.JikanNetworkDataSource
import com.edwin.network.jikan.model.JikanEpisodesResponse
import com.edwin.network.jikan.service.JikanService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitJikanNetwork @Inject constructor(
    private val service: JikanService
) : JikanNetworkDataSource {

    override suspend fun getEpisodeForAnime(malId: Int): JikanEpisodesResponse {
        return service.getAnimeEpisodes(malId)
    }
}