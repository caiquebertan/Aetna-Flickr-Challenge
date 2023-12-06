@file:OptIn(ExperimentalGlideComposeApi::class)

package com.caique.aetnatestflickr.feature.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.caique.aetnatestflickr.R
import com.caique.aetnatestflickr.data.model.PhotoItem
import com.caique.aetnatestflickr.ui.components.SearchToolbar
import com.caique.aetnatestflickr.ui.design.AppTheme
import com.caique.aetnatestflickr.ui.navigateToDetail
import org.koin.androidx.compose.getViewModel

@Composable
fun PhotoList(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: ListViewModel = getViewModel()

    val uiData by viewModel.uiListState.collectAsStateWithLifecycle()
    val (searchText, photos, searches, loading) = uiData

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchToolbar(
            searchQuery = searchText,
            onSearchTriggered = {
                viewModel.search(it)
            },
            suggestions = searches
        )
        when {
            loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .testTag("loading")) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            searchText.isNotBlank() -> {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("success"),
                    columns = StaggeredGridCells.Fixed(2),   //Adaptive(180.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(photos) { photo ->
                        FlickrItem(
                            photo = photo,
                            navigateToDetail = navController::navigateToDetail
                        )
                    }
                }
            }
            searches.isEmpty() -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .testTag("empty")) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(R.string.no_searches_yet),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun FlickrItem(
    photo: PhotoItem,
    navigateToDetail: (PhotoItem) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClickLabel = stringResource(R.string.see_details)
            ) {
                navigateToDetail(photo)
            }
            .semantics(mergeDescendants = true) {}
            .testTag("item")

    ) {
        Column {

            GlideImage(
                model = photo.media.m,
                contentDescription = photo.title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                loading = placeholder(R.drawable.ic_launcher_foreground),
                failure = placeholder(R.drawable.ic_launcher_background)
            )

            Text(
                text = photo.title,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()

            )
        }
    }
}

@Composable
@Preview
fun ListPreview() {
    AppTheme {
        PhotoList(
            navController = rememberNavController()
        )
    }
}

