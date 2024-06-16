package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media

class MediaPreviewParameterProvider : PreviewParameterProvider<Media> {
    override val values = sequenceOf(
        // TV Series Examples
        // --------------------

        // Finished TV Series
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

        // Ongoing TV Series (Next Episode Known)
        Media.TvSeries(
            id = 166531,
            title = "Oshi no Ko Season 2",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx166531-dAL5MsqDHUkj.jpg",
            averageScore = null,
            popularity = 55762,
            startDate = 2024,
            averageColorHex = "#e44343",
            episodes = 0,
            nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                episode = 1,
                timeUntilAiring = 1505145
            )
        ),

        // Ongoing TV Series (Next Episode Unknown)
        Media.TvSeries(
            id = 153406,
            title = "Tower of God Season 2",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx153406-P4etA7704JEs.png",
            averageScore = null,
            popularity = 40761,
            startDate = 2024,
            averageColorHex = "#6b281a",
            episodes = 0,
            nextAiringEpisode = null
        ),

        // TV Series with a very high score
        Media.TvSeries(
            id = 154587,
            title = "Frieren: Beyond Journey's End",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx154587-gHSraOSa0nBG.jpg",
            averageScore = 95, // High score
            popularity = 190063,
            startDate = 2023,
            averageColorHex = "#aee493",
            episodes = 28,
            nextAiringEpisode = null
        ),

        // TV Series with a very long title
        Media.TvSeries(
            id = 139825,
            title = "Our Last Crusade or the Rise of a New World Season 2: The Epic Continuation With a Title That is Designed To Be Very Long",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx139825-rFlrzdRZmlYK.jpg",
            averageScore = 75,
            popularity = 16631,
            startDate = 2024,
            averageColorHex = "#e47843",
            episodes = 12,
            nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                episode = 3,
                timeUntilAiring = 2118945
            )
        ),

        // Movie Examples
        // ----------------

        // Movie with a standard duration
        Media.Movie(
            id = 21519,
            title = "Your Name.",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx21519-fPhvy69vnQqS.png",
            averageScore = 85,
            popularity = 524278,
            startDate = 2016,
            averageColorHex = "#0da1e4",
            duration = 107
        ),

        // Movie with a very long duration
        Media.Movie(
            id = 7311,
            title = "The Disappearance of Haruhi Suzumiya",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx7311-9yOqvUKyflJG.jpg",
            averageScore = 86,
            popularity = 117080,
            startDate = 2010,
            averageColorHex = "#f1d6bb",
            duration = 200 // Long duration (in minutes)
        )
    )
}