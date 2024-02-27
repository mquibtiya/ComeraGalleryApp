package com.comera.gallery.model

import com.comera.gallery.domain.MediaItem

data class Album(
    val name: String,
    val mediaItems: List<MediaItem>
)
