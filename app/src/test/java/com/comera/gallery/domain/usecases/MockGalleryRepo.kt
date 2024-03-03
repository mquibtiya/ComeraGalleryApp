package com.comera.gallery.domain.usecases

import android.database.ContentObserver
import android.net.Uri
import com.comera.gallery.domain.GalleryRepository
import com.comera.gallery.domain.MediaItem

class MockGalleryRepo : GalleryRepository {
    override suspend fun loadImages(): List<MediaItem> {
        val imageItems = mutableListOf<MediaItem>()
        imageItems.add(MediaItem(1, Uri.parse("image_uri1"), "name1", 1709484942605, 1L, "bucket1", false))
        imageItems.add(MediaItem(2, Uri.parse("image_uri2"), "name2", 1709484942604, 2L, "bucket2", false))
        imageItems.add(MediaItem(3, Uri.parse("image_uri3"), "name3", 1709484942603, 3L, "bucket3", false))
        return imageItems
    }

    override suspend fun loadVideos(): List<MediaItem> {
        val videoItems = mutableListOf<MediaItem>()
        videoItems.add(MediaItem(1, Uri.parse("video_uri1"), "name1", 1709484942603, 1L, "bucket1", true))
        videoItems.add(MediaItem(2, Uri.parse("video_uri2"), "name2", 1709484942604, 2L, "bucket2", true))
        videoItems.add(MediaItem(3, Uri.parse("video_uri3"), "name3", 1709484942605, 3L, "bucket3", true))
        return videoItems
    }

    override fun registerMediaContentProvider(contentObserver: ContentObserver) {

    }

    override fun unregisterMediaContentProvider(contentObserver: ContentObserver) {

    }
}