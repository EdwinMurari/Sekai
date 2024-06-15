package com.edwin.network.apollo

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.MediaNetworkDataSource
import com.edwin.sekai.GetTrendingAndPopularQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AniListMediaNetwork @Inject constructor(
    private val apolloClient: ApolloClient
) : MediaNetworkDataSource {

    override suspend fun getTrendingAndPopularMedia() =
        apolloClient.query(GetTrendingAndPopularQuery()).execute()
}