package com.edwin.data

import com.apollographql.apollo3.api.ApolloResponse
import com.benasher44.uuid.Uuid
import com.edwin.data.mapper.asNetworkModel
import com.edwin.data.model.MediaDetails
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.repository.impl.OneOffMediaRepository
import com.edwin.network.anilist.AnilistNetworkDataSource
import com.edwin.network.anilist.GetMediaDetailsByIdQuery
import com.edwin.network.anilist.GetTrendingAndPopularQuery
import com.edwin.network.anilist.fragment.MediaDetailsFragment
import com.edwin.network.anilist.fragment.MediaFragment
import com.edwin.network.anilist.type.MediaFormat
import com.edwin.network.jikan.JikanNetworkDataSource
import com.edwin.network.jikan.model.JikanEpisodeData
import com.edwin.network.jikan.model.JikanEpisodesResponse
import com.edwin.network.kitsu.GetEpisodeForAnilistMediaIdQuery
import com.edwin.network.kitsu.KitsuNetworkDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import com.apollographql.apollo3.api.Error as ApolloError

@ExperimentalCoroutinesApi
class OneOffMediaRepositoryTest {

    private val networkDataSource = mockk<AnilistNetworkDataSource>()
    private val jikanNetworkDataSource = mockk<JikanNetworkDataSource>()
    private val kitsuNetworkDataSource = mockk<KitsuNetworkDataSource>()
    private val repository = OneOffMediaRepository(
        anilistDataSource = networkDataSource,
        jikanDataSource = jikanNetworkDataSource,
        kitsuDataSource = kitsuNetworkDataSource
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

    @Test
    fun `getMediaById success for TV Series`() = runTest {
        val mockAnimeId = 12345
        val mockMalId = 234
        val mockMediaFragment = mockk<MediaFragment>(relaxed = true) {
            every { id } returns mockAnimeId
            every { format } returns MediaFormat.TV
        }
        val mockMediaDetailsFragment = mockk<MediaDetailsFragment>(relaxed = true) {
            every { idMal } returns mockMalId
            every { mediaFragment } returns mockMediaFragment
        }
        val mockMedia = mockk<GetMediaDetailsByIdQuery.Media>(relaxed = true) {
            every { mediaDetailsFragment } returns mockMediaDetailsFragment
        }

        val mockMediaDetailsApolloResponse = ApolloResponse.Builder(
            requestUuid = Uuid.randomUUID(),
            operation = GetMediaDetailsByIdQuery(mockAnimeId),
            data = GetMediaDetailsByIdQuery.Data(mockMedia)
        ).build()

        val mockCommonEpisodeNumber = 1
        val mockJikanEpisodeNumber = 2
        val mockKitsuEpisodeNumber = 3

        val mockJikanEpisodeDataCommon = mockk<JikanEpisodeData>(relaxed = true) {
            every { malId } returns mockCommonEpisodeNumber
            every { filler } returns true
        }
        val mockJikanEpisodeData = mockk<JikanEpisodeData>(relaxed = true) {
            every { malId } returns mockJikanEpisodeNumber
            every { filler } returns true
        }

        val mockKitsuEpisodeDataCommon =
            mockk<GetEpisodeForAnilistMediaIdQuery.Node>(relaxed = true) {
                every { number } returns mockCommonEpisodeNumber
            }
        val mockKitsuEpisodeData = mockk<GetEpisodeForAnilistMediaIdQuery.Node>(relaxed = true) {
            every { number } returns mockKitsuEpisodeNumber
        }

        val mockJikanEpisodes = mockk<JikanEpisodesResponse>(relaxed = true) {
            every { data } returns listOf(mockJikanEpisodeDataCommon, mockJikanEpisodeData)
        }

        val mockKitsuEpisodes = mockk<GetEpisodeForAnilistMediaIdQuery.Episodes>(relaxed = true) {
            every { nodes } returns listOf(mockKitsuEpisodeDataCommon, mockKitsuEpisodeData)
        }
        val mockOnAnime = mockk<GetEpisodeForAnilistMediaIdQuery.OnAnime>(relaxed = true) {
            every { episodes } returns mockKitsuEpisodes
        }
        val mockLookupMapping = mockk<GetEpisodeForAnilistMediaIdQuery.LookupMapping>(
            relaxed = true
        ) { every { onAnime } returns mockOnAnime }

        val mockKitsuApolloResponse = ApolloResponse.Builder(
            requestUuid = Uuid.randomUUID(),
            operation = GetEpisodeForAnilistMediaIdQuery(mockAnimeId.toString()),
            data = GetEpisodeForAnilistMediaIdQuery.Data(mockLookupMapping)
        ).build()

        coEvery {
            networkDataSource.getMediaById(mockAnimeId)
        } returns flow { emit(mockMediaDetailsApolloResponse) }

        coEvery {
            kitsuNetworkDataSource.getEpisodeForAnime(mockAnimeId.toString())
        } returns mockKitsuApolloResponse

        coEvery {
            jikanNetworkDataSource.getEpisodeForAnime(mockMalId)
        } returns mockJikanEpisodes

        val flow = repository.getMediaById(mockAnimeId)

        flow.collect { response ->
            if (response is NetworkResponse.Success) {
                val episodes = (response.data as? MediaDetails.TvSeries)?.episodes
                // First episode
                assertEquals(mockCommonEpisodeNumber, episodes?.get(0)?.number)
                assertEquals(true, episodes?.get(0)?.filler)

                // Second episode
                assertEquals(mockJikanEpisodeNumber, episodes?.get(1)?.number)
                assertEquals(true, episodes?.get(1)?.filler)

                // Third episode
                assertEquals(mockKitsuEpisodeNumber, episodes?.get(2)?.number)
                assertEquals(false, episodes?.get(2)?.filler)
            } else {
                Assert.fail("Response should be successful")
            }
        }

        coVerify { networkDataSource.getMediaById(mockAnimeId) }
        coVerify { kitsuNetworkDataSource.getEpisodeForAnime(mockAnimeId.toString()) }
        coVerify { jikanNetworkDataSource.getEpisodeForAnime(mockMalId) }
    }
}