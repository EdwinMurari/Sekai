package com.edwin.network

import com.apollographql.apollo3.api.ApolloResponse
import com.edwin.sekai.GetTrendingAndPopularQuery

interface MediaNetworkDataSource {

    suspend fun getTrendingAndPopularMedia(): ApolloResponse<GetTrendingAndPopularQuery.Data>
}