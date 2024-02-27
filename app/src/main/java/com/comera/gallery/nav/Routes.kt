package com.comera.gallery.nav

sealed class Routes(val screen: String) {
    object Splash: Routes("SPLASH_SCREEN")
    object AlbumView: Routes("ALBUM_SCREEN")
    object AlbumDetailView: Routes("ALBUM_DETAIL_SCREEN")
    object PhotoDetailView: Routes("PHOTO_DETAIL_SCREEN")
}
