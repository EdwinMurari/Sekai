package com.edwin.data.model

data class MediaPageResponse(
    val pageInfo: PageInfo,
    val media: List<Media>
)

data class PageInfo(
    val hasNextPage: Boolean
)