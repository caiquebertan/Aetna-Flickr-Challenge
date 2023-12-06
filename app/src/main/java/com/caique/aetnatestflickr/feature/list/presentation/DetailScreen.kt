@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)

package com.caique.aetnatestflickr.feature.list.presentation

import android.content.res.Configuration
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.caique.aetnatestflickr.R
import com.caique.aetnatestflickr.data.model.Media
import com.caique.aetnatestflickr.data.model.PhotoItem
import com.caique.aetnatestflickr.ui.design.AppTheme

@Composable
fun DetailScreen(photoItem: PhotoItem) {
    val configuration = LocalConfiguration.current

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeDetail(photo = photoItem)
    } else {
        PortraitDetail(photo = photoItem)
    }
}

@Composable
fun PortraitDetail(photo: PhotoItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        GlideImage(
            model = photo.media.m,
            contentDescription = photo.title,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.ic_launcher_foreground),
            failure = placeholder(R.drawable.ic_launcher_background)
        )

        // Photo details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = photo.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = "Description",
                textAlign = TextAlign.Center
            )
            val context = LocalContext.current
            AndroidView(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp)),
                factory = {
                    WebView(context).apply {
                        loadData(photo.description,"text/html; charset=UTF-8", null)
                    }
                }
            )

            val widthAndHeight = photo.getWidthAndHeight()
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                text = "Width: ${widthAndHeight?.first}"
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Height: ${widthAndHeight?.second}",
                textAlign = TextAlign.Center
            )
            Column(Modifier.semantics(mergeDescendants = true){}) {

                Text(text = "Author")

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = photo.author
                )
            }

            Column(Modifier.semantics(mergeDescendants = true){}) {

                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Tags"
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    photo.getTags().forEach {
                        SuggestionChip(
                            onClick = {},
                            label = { Text(text = it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LandscapeDetail(photo: PhotoItem) {
    // Fullscreen image without details
    GlideImage(
        model = photo.media.m,
        contentDescription = photo.title,
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Fit,
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_background)
    )
}

@Preview(showBackground = true)
@Composable
fun PhotoDetailScreenPreview() {
    AppTheme {
        DetailScreen(
            PhotoItem(
                title = "Title",
                author = "Caique Bertan",
                description = "<p>Lorem ipsum blablabla ".repeat(5),
                media = Media("https://google.com"),
                tags ="teste 1 2 3 testando bigtexttagtoseehowitgoes"
            ))
    }
}