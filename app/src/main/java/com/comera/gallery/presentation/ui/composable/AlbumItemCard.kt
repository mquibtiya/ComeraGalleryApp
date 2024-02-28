package com.comera.gallery.presentation.ui.composable

import androidx.compose.runtime.Composable
import com.comera.gallery.domain.MediaItem
import com.comera.gallery.presentation.viewmodel.GalleryViewModel


@Composable
fun AlbumItemCard(
    id: Long, mediaItemsForAlbum: List<MediaItem>, viewModel: GalleryViewModel, onClick: () -> Unit
) {
    val albumName = when (id) {
        viewModel.ALL_IMAGES_BUCKET_ID -> "All Images"
        viewModel.ALL_VIDEOS_BUCKET_ID -> "All Videos"
        else -> mediaItemsForAlbum[0].bucketName
    }

    val mediaItemCount = mediaItemsForAlbum.size
    val uriLatestMediaItem = mediaItemsForAlbum[0].contentUri

    SingleMediaItemView(
        uri = uriLatestMediaItem,
        itemName = albumName,
        itemCount = mediaItemCount,
        onClick = onClick
    )
}