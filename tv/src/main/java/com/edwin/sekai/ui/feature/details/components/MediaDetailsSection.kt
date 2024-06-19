package com.edwin.sekai.ui.feature.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.edwin.data.model.MediaDetails
import com.edwin.sekai.R
import com.edwin.sekai.ui.TvPreview
import com.edwin.sekai.ui.designsystem.component.PreviewAbleSubComposeImage
import com.edwin.sekai.ui.designsystem.previewprovider.MediaDetailsPreviewParameterProvider
import com.edwin.sekai.ui.designsystem.theme.SekaiTheme
import com.edwin.sekai.ui.utils.GenreList
import com.edwin.sekai.ui.utils.MediaDescription
import com.edwin.sekai.ui.utils.MediaMetaDataDetailed
import com.edwin.sekai.ui.utils.MediaTitles
import kotlinx.coroutines.launch

// Constants
const val COVER_IMAGE_HEIGHT = 358

@OptIn(ExperimentalTvMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MediaDetailsSection(
    mediaDetails: MediaDetails,
    onClickWatch: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val episodeNumber = 20 // TODO :: Get the last watched episode number
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {
        Row {
            MediaCoverImage(
                modifier = Modifier.height(COVER_IMAGE_HEIGHT.dp),
                imageUrl = mediaDetails.media.coverImage,
                contentDescription = "${mediaDetails.media.title} cover image"
            )

            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                MediaDetailsColumn(
                    mediaDetails = mediaDetails,
                    episodeNumber = episodeNumber,
                    onClickWatch = onClickWatch,
                    bringIntoViewRequester = bringIntoViewRequester
                )
            }
        }
    }
}

@Composable
private fun MediaCoverImage(
    imageUrl: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    PreviewAbleSubComposeImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        previewImage = painterResource(id = R.drawable.naruto),
        modifier = modifier
            .aspectRatio(2f / 3f)
            .clip(RoundedCornerShape(8.dp))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MediaDetailsColumn(
    mediaDetails: MediaDetails,
    episodeNumber: Int,
    onClickWatch: (Int, Int) -> Unit,
    bringIntoViewRequester: BringIntoViewRequester
) {
    val coroutineScope = rememberCoroutineScope()
    val media = mediaDetails.media
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
            .fillMaxWidth()
    ) {
        GenreList(genres = media.genres)

        MediaTitles(mainTitle = media.title, mediaTitle = mediaDetails.fullTitle)

        MediaMetaDataDetailed(media)

        MediaDescription(description = media.description, maxLines = 5)

        WatchButton(
            modifier = Modifier.onKeyEvent {
                if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_DPAD_UP) {
                    coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
                }
                false
            },
            episodeNumber = episodeNumber,
            onClickWatch = { onClickWatch(media.id, it) }
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun WatchButton(
    modifier: Modifier = Modifier,
    episodeNumber: Int,
    onClickWatch: (Int) -> Unit
) {
    Button(
        onClick = { onClickWatch(episodeNumber) },
        modifier = modifier.padding(top = 24.dp),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        Icon(
            imageVector = Icons.Rounded.PlayArrow,
            contentDescription = null
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.watch_now),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@TvPreview
@Composable
fun PreviewMediaDetails(
    @PreviewParameter(MediaDetailsPreviewParameterProvider::class) mediaDetails: MediaDetails
) {
    SekaiTheme {
        MediaDetailsSection(mediaDetails = mediaDetails, onClickWatch = { _, _ -> })
    }
}