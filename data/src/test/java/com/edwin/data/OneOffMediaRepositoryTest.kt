package com.edwin.data

import com.apollographql.apollo3.api.ApolloResponse
import com.benasher44.uuid.Uuid
import com.edwin.data.mapper.asNetworkModel
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.repository.OneOffMediaRepository
import com.edwin.network.anilist.GetTrendingAndPopularQuery
import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.jikan.JikanNetworkDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import com.apollographql.apollo3.api.Error as ApolloError

@ExperimentalCoroutinesApi
class OneOffMediaRepositoryTest {

    private val networkDataSource = mockk<AnilistNetworkDataSource>()
    private val jikanNetworkDataSource = mockk<JikanNetworkDataSource>()
    private val repository = OneOffMediaRepository(
        networkDataSource = networkDataSource,
        jikanDataSource = jikanNetworkDataSource
    )

    private val mediaSeason = MediaSeason.FALL
    private val seasonYear = 2024
    private val networkMediaSeason = mediaSeason.asNetworkModel()

    @Test
    fun `getTrendingAndPopularMedia - Success`() = runTest {
        val mockAnimeId = 1
        val mockMediaFragment = mockk<MediaFragment>(relaxed = true) {
            every { id } returns mockAnimeId
        }
        val mockMedium = mockk<GetTrendingAndPopularQuery.Medium> {
            every { mediaFragment } returns mockMediaFragment
        }
        val mockTrendingAnime = mockk<GetTrendingAndPopularQuery.TrendingAnimeThisSeason> {
            every { media } returns listOf(mockMedium)
        }
        val mockApolloResponseData = mockk<GetTrendingAndPopularQuery.Data>(relaxed = true) {
            every { trendingAnimeThisSeason } returns mockTrendingAnime
        }

        val mockApolloResponse = ApolloResponse.Builder(
            requestUuid = Uuid.randomUUID(),
            operation = GetTrendingAndPopularQuery(networkMediaSeason, seasonYear),
            data = mockApolloResponseData
        ).build()

        coEvery {
            networkDataSource.getTrendingAndPopularMedia(networkMediaSeason, seasonYear)
        } returns mockApolloResponse

        val flow = repository.getTrendingAndPopularMedia(mediaSeason, seasonYear)

        flow.collect { response ->
            if (response is NetworkResponse.Success) {
                assertEquals(mockAnimeId, response.data.trendingTvSeries?.firstOrNull()?.id)
            } else {
                Assert.fail("Response should be successful")
            }
        }

        coVerify { networkDataSource.getTrendingAndPopularMedia(networkMediaSeason, seasonYear) }
    }

    @Test
    fun `getTrendingAndPopularMedia - Failure with Errors`() = runTest {
        val errorMessage = "Error message"

        val mockError = mockk<ApolloError> { every { message } returns errorMessage }
        val mockApolloResponse = ApolloResponse.Builder(
            requestUuid = Uuid.randomUUID(),
            operation = GetTrendingAndPopularQuery(networkMediaSeason, seasonYear),
            data = null
        ).apply {
            errors(errors = listOf(mockError))
        }.build()

        coEvery {
            networkDataSource.getTrendingAndPopularMedia(networkMediaSeason, seasonYear)
        } returns mockApolloResponse

        val flow = repository.getTrendingAndPopularMedia(mediaSeason, seasonYear)

        flow.collect { response ->
            assertEquals(
                errorMessage,
                (response as? NetworkResponse.Error)?.errors?.firstOrNull()?.message
            )
        }

        coVerify { networkDataSource.getTrendingAndPopularMedia(networkMediaSeason, seasonYear) }
    }
}