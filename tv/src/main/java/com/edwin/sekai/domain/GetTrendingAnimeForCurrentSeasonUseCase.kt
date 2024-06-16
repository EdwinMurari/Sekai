package com.edwin.sekai.domain

import com.edwin.data.model.AllMediaList
import com.edwin.data.model.MediaSeason
import com.edwin.data.model.NetworkResponse
import com.edwin.data.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetTrendingAnimeForCurrentSeasonUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    operator fun invoke(): Flow<NetworkResponse<AllMediaList>> {
        val (season, seasonYear) = getCurrentSeasonAndYear()
        return repository.getTrendingAndPopularMedia(season, seasonYear)
    }

    // Helper function to get the current season and year
    private fun getCurrentSeasonAndYear(): Pair<MediaSeason, Int> {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val season = when (month) {
            in Calendar.MARCH..Calendar.MAY -> MediaSeason.SPRING
            in Calendar.JUNE..Calendar.AUGUST -> MediaSeason.SUMMER
            in Calendar.SEPTEMBER..Calendar.NOVEMBER -> MediaSeason.FALL
            else -> MediaSeason.WINTER
        }
        return Pair(season, year)
    }
}