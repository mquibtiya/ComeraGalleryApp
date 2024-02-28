package com.comera.gallery.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.comera.gallery.presentation.ui.composable.AlbumDetailView
import com.comera.gallery.presentation.ui.composable.GalleryPermissionsScreen
import com.comera.gallery.presentation.ui.composable.GalleryScreen

@Composable
fun GalleryAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.Splash.screen,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Splash.screen) {
            GalleryPermissionsScreen(navController = navController)
        }
        composable(Routes.AlbumView.screen) {
            GalleryScreen(navController)
        }
        composable(Routes.AlbumDetailView.screen + "/{albumId}",
            arguments = listOf(
                navArgument("albumId") {
                    type = NavType.LongType
                    defaultValue = 923645
                }
            )) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getLong("albumId") ?: 923645
            Log.d("Mariya", "albumId = $albumId")
            AlbumDetailView(
                navController = navController,
                albumId = albumId
            )
        }
    }
}