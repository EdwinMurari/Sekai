package com.edwin.sekai.ui.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardColors
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import androidx.tv.material3.contentColorFor
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme

@Composable
fun MediaCard(
    media: Media,
    modifier: Modifier = Modifier,
    onClick: (Media) -> Unit = {}
) {
    Card(
        onClick = { onClick(media) },
        modifier = modifier
            .heightIn(max = 234.dp)
            .aspectRatio(9f / 16f),
        colors = mediaCardColors()
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 12.dp)
        ) {
            PreviewAbleSubComposeImage(
                imageUrl = media.coverImage,
                previewImage = painterResource(id = R.drawable.naruto),
                contentDescription = "${media.title} coverImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .weight(1f)
            )

            "Something"?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 12.dp)
                        .padding(top = 4.dp),
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            } ?: Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp)
                    .padding(top = 4.dp),
                text = media.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp),
                text = "SubTitle",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun mediaCardColors(): CardColors {
    val containerColor = MaterialTheme.colorScheme.tertiaryContainer
    val focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
    val pressedContainerColor = MaterialTheme.colorScheme.tertiaryContainer

    return CardDefaults.colors(
        containerColor = containerColor,
        contentColor = contentColorFor(containerColor),
        focusedContainerColor = containerColor,
        focusedContentColor = contentColorFor(focusedContainerColor),
        pressedContainerColor = focusedContainerColor,
        pressedContentColor = contentColorFor(pressedContainerColor)
    )
}

@TvPreview
@Composable
fun MediaCardPreview() {
    SekaiTheme {
        MediaCard(
            media = Media.TvSeries(
                id = 175977,
                title = "My Deer Friend Nokotan",
                coverImage = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx175977-kRTEKLQiEBUJ.jpg",
                averageScore = 1,
                popularity = 12782,
                startDate = 2024,
                episodes = 0,
                nextAiringEpisode = Media.TvSeries.NextAiringEpisode(
                    episode = 1,
                    timeUntilAiring = 1858943
                )
            )
        )
    }
}