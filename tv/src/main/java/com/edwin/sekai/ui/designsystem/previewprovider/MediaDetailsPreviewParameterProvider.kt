package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.Media
import com.edwin.data.model.MediaDetails

class MediaDetailsPreviewParameterProvider : PreviewParameterProvider<MediaDetails> {
    override val values: Sequence<MediaDetails>
        get() = sequenceOf(
            MediaDetails.TvSeries(
                media = Media.TvSeries(
                    id = 16498,
                    title = "Attack on Titan",
                    description = "Several hundred years ago, humans were nearly exterminated by titans...",
                    coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx16498-73IhOXpJZiMF.jpg",
                    bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/16498-8jpFCOcDmneX.jpg",
                    genres = listOf("Action", "Drama", "Fantasy", "Mystery"),
                    rawAverageScore = 84,
                    popularity = 774584,
                    startDate = 2013,
                    averageColorHex = "#f1a143",
                    episodesCount = 4,
                    nextAiringEpisode = null
                ),
                fullTitle = MediaDetails.Title(
                    english = "Attack on Titan",
                    romaji = "Shingeki no Kyojin",
                    native = "進撃の巨人"
                ),
                relations = listOf(
                    MediaDetails.MediaRelation(
                        relationType = "Adaptation",
                        media = Media.TvSeries(
                            id = 53390,
                            title = "Attack on Titan (Manga)",
                            description = "In this post-apocalyptic sci-fi story...",
                            coverImage = "https://s4.anilist.co/file/anilistcdn/media/manga/cover/medium/bx53390-1RsuABC34P9D.jpg",
                            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/manga/banner/53390-6Uru5rrjh8zv.jpg",
                            genres = listOf("Action", "Drama", "Fantasy", "Mystery"),
                            rawAverageScore = 84,
                            popularity = 194340,
                            startDate = 2009,
                            averageColorHex = "#d6431a",
                            episodesCount = null,
                            nextAiringEpisode = null
                        )
                    ),
                    MediaDetails.MediaRelation(
                        relationType = "Alternative",
                        media = Media.Movie(
                            id = 20691,
                            title = "Attack on Titan Part I: Crimson Bow and Arrow",
                            description = "A recompilation of the anime series...",
                            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx20691-dnv0rkpbgBDJ.png",
                            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/20691-xKJkvnhezBb0.jpg",
                            genres = listOf("Action", "Drama", "Fantasy"),
                            rawAverageScore = 74,
                            popularity = 22632,
                            startDate = 2014,
                            averageColorHex = "#e47835",
                            duration = 119
                        )
                    )
                ),
                recommendations = listOf(
                    Media.TvSeries(
                        id = 16498, // Example: Recommending the same series
                        title = "Attack on Titan",
                        description = "Several hundred years ago...",
                        coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx16498-73IhOXpJZiMF.jpg",
                        bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/16498-8jpFCOcDmneX.jpg",
                        genres = listOf("Action", "Drama", "Fantasy", "Mystery"),
                        rawAverageScore = 84,
                        popularity = 774584,
                        startDate = 2013,
                        averageColorHex = "#f1a143",
                        episodesCount = 25, // Example episode count
                        nextAiringEpisode = null
                    ),
                    Media.Movie(
                        id = 21,
                        title = "Spirited Away",
                        description = "Chihiro Ogino, a sullen ten-year-old girl...",
                        coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/nx21-uJ5TvnBVN79u.jpg",
                        bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/21-rW26Z1a2zM4h.jpg",
                        genres = listOf("Adventure", "Supernatural", "Drama"),
                        rawAverageScore = 86,
                        popularity = 421211,
                        startDate = 2001,
                        averageColorHex = "#f1d6a1",
                        duration = 125 // Example duration
                    )
                ),
                episodes = PreviewParameterData.episodes
            ),

            // Naruto: Shippuden (TV Series) Example
            MediaDetails.Movie(
                media = Media.Movie(
                    id = 1735,
                    title = "Naruto: Shippuden",
                    description = "Naruto: Shippuuden is the continuation of the original...",
                    coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx1735-tdIgU840YJHA.png",
                    bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/1735.jpg",
                    genres = listOf(
                        "Action", "Adventure", "Comedy", "Drama", "Fantasy", "Supernatural"
                    ),
                    rawAverageScore = 81,
                    popularity = 459035,
                    startDate = 2007,
                    averageColorHex = "#e4865d",
                    duration = 1234
                ),
                fullTitle = MediaDetails.Title(
                    english = "Naruto: Shippuden",
                    romaji = "NARUTO: Shippuuden",
                    native = "NARUTO -ナルト- 疾風伝"
                ),
                relations = listOf(
                    MediaDetails.MediaRelation(
                        relationType = "Prequel",
                        media = Media.TvSeries(
                            id = 20,
                            title = "Naruto",
                            description = "Naruto Uzumaki, a hyperactive and knuckle-headed ninja...",
                            coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx20-LxrhhIQyiE60.jpg",
                            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/20-HHxhPj5JD13a.jpg",
                            genres = listOf(
                                "Action", "Adventure", "Comedy", "Drama", "Fantasy", "Supernatural"
                            ),
                            rawAverageScore = 79,
                            popularity = 538991,
                            startDate = 2002,
                            averageColorHex = "#e47850",
                            episodesCount = 220, // Example episode count
                            nextAiringEpisode = null
                        )
                    ),
                    MediaDetails.MediaRelation(
                        relationType = "Adaptation",
                        media = Media.TvSeries(
                            id = 30011,
                            title = "Naruto (Manga)",
                            description = "Before Naruto's birth...",
                            coverImage = "https://s4.anilist.co/file/anilistcdn/media/manga/cover/medium/nx30011-9yUF1dXWgDOx.jpg",
                            bannerImage = "https://s4.anilist.co/file/anilistcdn/media/manga/banner/30011-pkX1O0EFqvV7.jpg",
                            genres = listOf("Action", "Adventure", "Supernatural"),
                            rawAverageScore = 79,
                            popularity = 77996,
                            startDate = 1999,
                            averageColorHex = "#fe7828",
                            episodesCount = null, // Manga doesn't have episodes
                            nextAiringEpisode = null
                        )
                    )
                ),
                recommendations = listOf(
                    Media.TvSeries(
                        id = 1735, // Example: Recommending itself
                        title = "Naruto: Shippuden",
                        description = "Naruto: Shippuden is the continuation of the original...",
                        coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx1735-tdIgU840YJHA.png",
                        bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/1735.jpg",
                        genres = listOf(
                            "Action", "Adventure", "Comedy", "Drama", "Fantasy", "Supernatural"
                        ),
                        rawAverageScore = 81,
                        popularity = 459035,
                        startDate = 2007,
                        averageColorHex = "#e4865d",
                        episodesCount = 500, // Example episode count
                        nextAiringEpisode = null
                    ),
                    Media.TvSeries(
                        id = 20,
                        title = "Naruto",
                        description = "Naruto Uzumaki, a hyperactive and knuckle-headed ninja...",
                        coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx20-LxrhhIQyiE60.jpg",
                        bannerImage = "https://s4.anilist.co/file/anilistcdn/media/anime/banner/20-HHxhPj5JD13a.jpg",
                        genres = listOf(
                            "Action",
                            "Adventure",
                            "Comedy",
                            "Drama",
                            "Fantasy",
                            "Supernatural"
                        ),
                        rawAverageScore = 79,
                        popularity = 538991,
                        startDate = 2002,
                        averageColorHex = "#e47850",
                        episodesCount = 220, // Example episode count
                        nextAiringEpisode = null
                    )
                )
            )
        )
}