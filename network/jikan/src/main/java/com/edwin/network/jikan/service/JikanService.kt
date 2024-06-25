package com.edwin.network.jikan.service

import com.edwin.network.jikan.model.JikanEpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanService {

    @GET("anime/{malId}/episodes")
    suspend fun getAnimeEpisodes(@Path("malId") malId: Int): JikanEpisodesResponse
}