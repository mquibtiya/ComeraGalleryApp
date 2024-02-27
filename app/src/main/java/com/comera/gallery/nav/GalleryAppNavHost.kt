package com.comera.gallery.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.comera.gallery.ui.composable.AlbumDetailView
import com.comera.gallery.ui.composable.GalleryScreen

@Composable
fun GalleryAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.AlbumView.screen,
    // other parameters
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.AlbumView.screen) {
            GalleryScreen(navController)
        }
        composable(Routes.AlbumDetailView.screen) {
            AlbumDetailView(navController)
        }
    }
}