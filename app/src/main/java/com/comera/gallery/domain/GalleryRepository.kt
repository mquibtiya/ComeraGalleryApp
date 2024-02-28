package com.comera.gallery.domain

import android.database.ContentObserver

interface GalleryRepository {
    fun loadImages(): List<MediaItem>
    fun loadVideos(): List<MediaItem>

    fun registerMediaContentProvider(contentObserver: ContentObserver)

    fun unregisterMediaContentProvider(contentObserver: ContentObserver)
}