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
            episodesCount = 26,
            nextAiringEpisode = null,
            description = "Robin Sena is a powerful craft user drafted into the STNJ - a group of specialized hunters that fight deadly beings known as Witches. Though her fire power is great, she’s got a lot to learn about her powers and working withher cool and aloof partner, Amon. But the truth about the Witches and herself will leave Robin on an entirely new path that she never expected!\n\n(Source: Funimation)",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx7-6uh1fPvbgS9t.png",
            genres = listOf("Action", "Adventure", "Fantasy", "Romance")
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
            episodesCount = 0,
            nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                episode = 1,
                timeUntilAiring = 1505145
            ),
            description = "The second season of <i>[Oshi no Ko]</i>.",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx166531-dAL5MsqDHUkj.jpg",
            genres = listOf("Drama", "Romance", "Slice of Life", "Supernatural")
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
            episodesCount = 0,
            nextAiringEpisode = null,
            description = "The second season of <i>Kami no Tou: Tower of God</i>.",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx153406-P4etA7704JEs.png",
            genres = listOf("Adventure")
        ),

        // TV Series with a very high score
        Media.TvSeries(
            id = 154587,
            title = "Frieren: Beyond Journey's End",
            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx154587-gHSraOSa0nBG.jpg",
            averageScore = 95,
            popularity = 190063,
            startDate = 2023,
            averageColorHex = "#aee493",
            episodesCount = 28,
            nextAiringEpisode = null,
            description = "The adventure is over but life goes on for an elf mage just beginning to learn what living is all about. Elf mage Frieren and her courageous fellow adventurers have defeated the Demon King and brought peace to the land. But Frieren will long outlive the rest of her former party. How will she come to understand what life means to the people around her? Decades after their victory, the funeral of one her friends confronts Frieren with her own near immortality. Frieren sets out to fulfill the last wishes of her comrades and finds herself beginning a new adventure…\n\n(Source: Crunchyroll)",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx154587-gHSraOSa0nBG.jpg",
            genres = listOf("Adventure", "Drama", "Fantasy")
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
            episodesCount = 12,
            nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                episode = 3,
                timeUntilAiring = 2118945
            ),
            description = "The second season of <i>Kimi to Boku no Saigo no Senjou, Aruiwa Sekai ga Hajimaru Seisen</>.",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx139825-rFlrzdRZmlYK.jpg",
            genres = listOf("Action", "Comedy", "Romance")
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
            duration = 107,
            description = "Mitsuha Miyamizu, a high school girl, yearns to live the life of a boy in the bustling city of Tokyo—a dream that stands in stark contrast to her present life in the countryside. Meanwhile in the city, Taki Tachibana lives a busy life as a high school student while juggling his part-time job and hopes for a future in architecture.\n\nOne day, Mitsuha awakens in a room that is not her own and suddenly finds herself living the dream life in Tokyo—but in Taki's body! Elsewhere, Taki finds himself living Mitsuha's life in the humble countryside. In pursuit of an answer to this strange phenomenon, they begin to search for one another.\n\n<i>Kimi no Na wa.</i> revolves around Mitsuha and Taki's actions, which begin to have a dramatic impact on each other's lives, weaving them into a fabric held together by fate and circumstance.\n\n(Source: MAL Rewrite)",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx21519-fPhvy69vnQqS.png",
            genres = listOf("Drama", "Romance", "Supernatural")
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
            duration = 200, // Long duration (in minutes)
            description = "It is mid-December, and SOS Brigade chief Haruhi Suzumiya announces that the Brigade is going to hold a Christmas party in their clubroom, with Japanese hotpot for dinner. The brigade members Kyon, Yuki Nagato, Mikuru Asahina and Itsuki Koizumi start preparing everything for the party, such as costumes and decorations. But a couple of days later, Kyon arrives at school only to find that Haruhi is missing. Not only that, but Mikuru claims she has never known Kyon before, Koizumi is also missing, and Yuki has become the sole member of the literature club. The SOS Brigade seems to have never existed, nor has Haruhi Suzumiya. No one in the school has ever heard about her... except for Kyon.\n\n(Source: Anime News Network)",
            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx7311-9yOqvUKyflJG.jpg",
            genres = listOf("Drama", "Mystery", "Sci-Fi", "Supernatural")
        )
    )
}