package com.comera.gallery.model

import android.net.Uri

data class MediaItem(
    val id: Long,
    val contentUri: Uri,
    val displayName: String,
    val dateAdded: Long,
    val bucketId: Long,
    val bucketName: String,
)
