package com.comera.gallery.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.comera.gallery.nav.Routes
import com.comera.gallery.ui.viewmodel.GalleryViewModel


@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel()
) {
//    viewModel.mediaItems.collectAsState(emptyList()).value
    val albums = viewModel.albumMap.collectAsState().value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albums.keys.toList()) {

            val mediaItemsForAlbum = albums[it]
            if (mediaItemsForAlbum != null) {
                AlbumItemCard(
                    it,
                    mediaItemsForAlbum,
                    viewModel
                ) {
                    navController.navigate(Routes.AlbumDetailView.screen)
//                    (viewModel::onClickAlbum)(it, mediaItemsForAlbum)
                }
            }
        }
    }
}
