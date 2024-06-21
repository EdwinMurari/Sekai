package com.edwin.network.jikan.retrofit

import com.edwin.network.jikan.BuildConfig
import com.edwin.network.jikan.JikanNetworkDataSource
import com.edwin.network.jikan.model.JikanEpisodesResponse
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

private interface JikanService {

    @GET("anime/{malId}/episodes")
    suspend fun getAnimeEpisodes(@Path("malId") malId: Int): JikanEpisodesResponse
}

@Singleton
internal class RetrofitJikanNetwork @Inject constructor(
    json: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : JikanNetworkDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        // We use callFactory lambda here with dagger.Lazy<Call.Factory>
        // to prevent initializing OkHttp on the main thread.
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()

    private val service: JikanService = retrofit.create(JikanService::class.java)

    override suspend fun getEpisodeForAnime(malId: Int): JikanEpisodesResponse {
        return service.getAnimeEpisodes(malId)
    }
}