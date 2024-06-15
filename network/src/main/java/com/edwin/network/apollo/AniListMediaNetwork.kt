package com.edwin.network.apollo

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.GetTrendingAndPopularQuery
import com.edwin.network.MediaNetworkDataSource
import com.edwin.network.type.MediaSeason
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AniListMediaNetwork @Inject constructor(
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) : MediaNetworkDataSource {

    override suspend fun getTrendingAndPopularMedia(
        season: MediaSeason,
        seasonYear: Int
    ) = withContext(ioDispatcher) {
        apolloClient.query(
            GetTrendingAndPopularQuery(
                season,
                seasonYear
            )
        ).execute()
    }
}