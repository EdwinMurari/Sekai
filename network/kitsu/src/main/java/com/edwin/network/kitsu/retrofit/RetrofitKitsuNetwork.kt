package com.edwin.network.kitsu.retrofit

import com.edwin.network.kitsu.BuildConfig
import com.edwin.network.kitsu.KitsuNetworkDataSource
import com.edwin.network.kitsu.model.KitsuEpisodeResponse
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface KitsuService {

    @GET("episodes")
    suspend fun getAnimeEpisodes(
        @Query("filter[malId]") malId: Int,
    ): KitsuEpisodeResponse
}

@Singleton
internal class RetrofitKitsuNetwork @Inject constructor(
    json: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : KitsuNetworkDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.KITSU_API_URL)
        // We use callFactory lambda here with dagger.Lazy<Call.Factory>
        // to prevent initializing OkHttp on the main thread.
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()

    private val service: KitsuService = retrofit.create(KitsuService::class.java)

    override suspend fun getEpisodeForAnime(malId: Int): KitsuEpisodeResponse {
        return service.getAnimeEpisodes(malId)
    }
}