package com.comera.gallery.ui.activity

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.comera.gallery.domain.MediaItem
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
        registerImagesContentProvider()
        registerVideosContentProvider()
        setContent {
            GalleryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GalleryScreen(viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        unregisterContentProvider()
        super.onDestroy()
    }
}

@Composable
fun GalleryScreen(viewModel: GalleryViewModel) {
    viewModel.mediaItems.collectAsState(emptyList()).value
    val albums = viewModel.getAlbums()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(albums.keys.toList()) {

            val mediaItemsForAlbum = albums[it]
            if (mediaItemsForAlbum != null) {
                MediaItemCard(
                    it,
                    mediaItemsForAlbum,
                    viewModel
                ) { (viewModel::onClickAlbum)(it, mediaItemsForAlbum) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaItemCard(
    id: Long,
    mediaItemsForAlbum: List<MediaItem>,
    viewModel: GalleryViewModel,
    onClick: () -> Unit
) {
    val albumName =
        if (id == viewModel.ALL_IMAGES_BUCKET_ID) "All Images" else mediaItemsForAlbum[0].bucketName
    val mediaItemCount = mediaItemsForAlbum.size
    val uriLatestMediaItem = mediaItemsForAlbum[0].contentUri
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = uriLatestMediaItem),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
//                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium)
            )
            Text(text = albumName)
            Text(text = mediaItemCount.toString())
        }
    }
}