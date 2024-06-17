package com.edwin.sekai.ui.designsystem.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.edwin.data.model.MediaCollections

class MediaCollectionPreviewParameterProvider : PreviewParameterProvider<MediaCollections> {
    private val sampleTvSeriesList = PreviewParameterData.tvSeriesList
    private val sampleMoviesList = PreviewParameterData.movieList

    private val mediaCollections = MediaCollections(
        trendingTvSeries = sampleTvSeriesList,
        trendingMovies = sampleMoviesList,
        popularTvSeries = sampleTvSeriesList,
        topTvSeries = sampleTvSeriesList,
        trendingTvSeriesAllTime = sampleTvSeriesList,
        popularTvSeriesAllTime = sampleTvSeriesList,
        topTvSeriesAllTime = sampleTvSeriesList,
        popularMovies = sampleMoviesList,
        topMovies = sampleMoviesList,
        trendingMoviesAllTime = sampleMoviesList,
        popularMoviesAllTime = sampleMoviesList,
        topMoviesAllTime = sampleMoviesList
    )

    override val values = sequenceOf(mediaCollections)
}