package com.comera.gallery.ui.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.comera.gallery.nav.GalleryAppNavHost
import com.comera.gallery.theme.GalleryAppTheme
import com.comera.gallery.ui.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: GalleryViewModel by viewModels()

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            viewModel.loadMediaItems()
        }
    }

    private fun registerImagesContentProvider() {
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun registerVideosContentProvider() {
        contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun unregisterContentProvider() {
        contentResolver.unregisterContentObserver(contentObserver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GalleryAppNavHost(navController = rememberNavController())
//                    GalleryScreen()
                }
            }
        }
        registerImagesContentProvider()
        registerVideosContentProvider()
    }

    override fun onDestroy() {
        unregisterContentProvider()
        super.onDestroy()
    }
}