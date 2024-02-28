package com.comera.gallery.presentation.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.comera.gallery.presentation.viewmodel.GalleryViewModel

@Composable
fun AlbumDetailView(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel(),
    albumId: Long = viewModel.ALL_IMAGES_BUCKET_ID
) {
    val mediaItemsList = viewModel.getMediaItemsForAlbum(albumId) ?: return
    val albumName = when (albumId) {
        viewModel.ALL_IMAGES_BUCKET_ID -> "All Images"
        viewModel.ALL_VIDEOS_BUCKET_ID -> "All Videos"
        else -> mediaItemsList[0].bucketName
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        GalleryAppBar(
            title = albumName,
            showBackButton = true,
            navController = navController
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mediaItemsList) { mediaItem ->
                SingleMediaItemView(
                    uri = mediaItem.contentUri,
                    itemName = mediaItem.displayName
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryAppBar(title: String, showBackButton: Boolean, navController: NavController) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    navController.navigateUp()
                }
                ) {// go back to album view
                    Icon(Icons.Default.ArrowBack, "Back Button")
                }
            }
        }
    )
}