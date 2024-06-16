package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media

class MediaPreviewParameterProvider : PreviewParameterProvider<Media> {
    override val values = sequenceOf(
        // TV Series - Finished
        Media.TvSeries(
            id = 7,
            title = "Witch Hunter ROBIN",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx7-6uh1fPvbgS9t.png",
            averageScore = 68,
            popularity = 16793,
            startDate = 2002,
            averageColorHex = "#e4935d",
            episodes = 26,
            nextAiringEpisode = null
        ),
        // TV Series - Ongoing (Next Episode Known)
        Media.TvSeries(
            id = 166531,
            title = "Oshi no Ko Season 2",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx166531-dAL5MsqDHUkj.jpg",
            averageScore = 0, // Assuming score not available yet
            popularity = 55762,
            startDate = 2024,
            averageColorHex = "#e44343",
            episodes = 0,
            nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                episode = 1,
                timeUntilAiring = 1505145
            )
        ),
        // TV Series - Ongoing (Next Episode Unknown)
        Media.TvSeries(
            id = 153406,
            title = "Tower of God Season 2",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx153406-P4etA7704JEs.png",
            averageScore = 0,
            popularity = 40761,
            startDate = 2024,
            averageColorHex = "#6b281a",
            episodes = 0,
            nextAiringEpisode = null
        ),
        // Movie
        Media.Movie(
            id = 21519,
            title = "Your Name.",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx21519-fPhvy69vnQqS.png",
            averageScore = 85,
            popularity = 524278,
            startDate = 2016,
            averageColorHex = "#0da1e4",
            duration = 107
        )
    )
}