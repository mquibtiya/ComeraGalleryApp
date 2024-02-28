package com.comera.gallery.domain

import android.database.ContentObserver

interface GalleryRepository {
    suspend fun loadImages(): List<MediaItem>
    suspend fun loadVideos(): List<MediaItem>

    fun registerMediaContentProvider(contentObserver: ContentObserver)

    fun unregisterMediaContentProvider(contentObserver: ContentObserver)
}