package com.comera.gallery.presentation.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.comera.gallery.nav.Routes
import com.comera.gallery.presentation.viewmodel.GalleryViewModel


@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val albums = viewModel.albumMap.collectAsState().value

    Column(Modifier.fillMaxSize()) {
        GalleryAppBar(
            title = "Gallery",
            showBackButton = false,
            navController = navController
        )

        if (albums.isEmpty()) {
            EmptyScreen()
        } else {
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
                            navController.navigate(Routes.AlbumDetailView.screen + "/$it")
                        }
                    }
                }
            }
        }
    }
}
