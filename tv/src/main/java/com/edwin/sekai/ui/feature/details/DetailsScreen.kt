package com.edwin.sekai.ui.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.edwin.data.model.Media
import com.edwin.sekai.R
import com.edwin.sekai.ui.designsystem.previewprovider.PreviewParameterData
import com.edwin.sekai.ui.utils.formatMovieDuration
import com.edwin.sekai.ui.utils.getEpisodeInfo

@Composable
fun DetailsRoute(onWatchEpisodeClick: (String, Int) -> Unit) {
    DetailsScreen(PreviewParameterData.tvSeriesList.first())
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DetailsScreen(media: Media) {
    val director = "Alexandre Dimitrov"
    val writer = "Georgi Horvath"
    val screenplay = "Ben Jackson"

    // 2. Main Content
    Row(modifier = Modifier.padding(16.dp)) {
        // 3. Image (using Coil)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(media.bannerImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .aspectRatio(2 / 3f), // Adjust aspect ratio as needed
            contentScale = ContentScale.Crop
        )

        // 4. Details Column
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()) // Make details scrollable
        ) {
            Text(
                text = media.title ?: stringResource(R.string.title_missing),
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            media.description?.let { Text(text = it, style = TextStyle(fontSize = 16.sp)) }
            Spacer(modifier = Modifier.height(16.dp))

            // 5. Metadata Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${media.popularity} • ${media.startDate} (US) • ",
                    style = TextStyle(fontSize = 14.sp)
                )
                media.genres?.forEach { genre ->
                    Text("$genre • ", style = TextStyle(fontSize = 14.sp))
                }
                Text(
                    text = when (media) {
                        is Media.TvSeries -> getEpisodeInfo(media)
                        is Media.Movie -> formatMovieDuration(media.duration)
                    },
                    style = TextStyle(fontSize = 14.sp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 6. Crew Information
            Text("Director", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = director, style = TextStyle(fontSize = 14.sp))
            Spacer(modifier = Modifier.height(8.dp))

            Text("Writer", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = writer, style = TextStyle(fontSize = 14.sp))
            Spacer(modifier = Modifier.height(8.dp))

            Text("Screenplay", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = screenplay, style = TextStyle(fontSize = 14.sp))
            Spacer(modifier = Modifier.height(16.dp))

            // 7. Watch Now Button
            Button(onClick = { /* Handle play action */ }) {
                Text("▶ Watch now")
            }
        }
    }
    // ... You can add the "Cast & Crew" section below
}