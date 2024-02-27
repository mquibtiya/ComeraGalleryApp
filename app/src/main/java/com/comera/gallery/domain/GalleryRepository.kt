package com.comera.gallery.domain

interface GalleryRepository {
    fun loadImages(): List<MediaItem>
    fun loadVideos(): List<MediaItem>

}