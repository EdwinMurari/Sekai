package com.edwin.network

import com.apollographql.apollo3.ApolloClient
import com.edwin.network.anilist.BuildConfig
import com.edwin.network.anilist.apollo.ApolloAnilistNetworkDataSource
import com.edwin.network.anilist.type.MediaSeason
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AniListMediaNetworkTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var networkDataSource: ApolloAnilistNetworkDataSource

    private val mediaSeason = MediaSeason.FALL
    private val seasonYear = 2024

    @Before
    fun setup() {
        apolloClient = ApolloClient.Builder()
            .serverUrl(BuildConfig.ANILIST_GRAPHQL_URL)
            .build()
        networkDataSource = ApolloAnilistNetworkDataSource(apolloClient)
    }

    @Test
    fun `getTrendingAndPopularMedia - success with all fields having values`() = runTest {
        val response = networkDataSource.getTrendingAndPopularMedia(mediaSeason, seasonYear)

        response.data?.also { responseData ->
            assertNull(response.errors)

            with(responseData) {
                trendingAnimeThisSeason?.media.shouldNotBeEmpty()
                trendingMoviesThisSeason?.media.shouldNotBeEmpty()
                popularAnimeThisSeason?.media.shouldNotBeEmpty()
                topAnimeThisSeason?.media.shouldNotBeEmpty()
                trendingAnimeAllTime?.media.shouldNotBeEmpty()
                popularAnimeAllTime?.media.shouldNotBeEmpty()
                topAnimeAllTime?.media.shouldNotBeEmpty()
                popularMoviesThisSeason?.media.shouldNotBeEmpty()
                topMoviesThisSeason?.media.shouldNotBeEmpty()
                trendingMoviesAllTime?.media.shouldNotBeEmpty()
                popularMoviesAllTime?.media.shouldNotBeEmpty()
                topMoviesAllTime?.media.shouldNotBeEmpty()
            }
        } ?: fail("Response data is null")
    }

    private fun <T> List<T>?.shouldNotBeEmpty() {
        assertNotNull("List should not be null", this)
        assertTrue("List should not be empty", this?.isNotEmpty() == true)
    }
}