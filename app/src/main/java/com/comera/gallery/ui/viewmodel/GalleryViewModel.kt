package com.comera.gallery.ui.viewmodel

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.comera.gallery.model.Album
import com.comera.gallery.model.Albums
import com.comera.gallery.model.MediaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> get() = _mediaItems

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadMediaItems()
        }
    }

    init {
        loadMediaItems()
        getApplication<Application>().contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
        getApplication<Application>().contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun loadMediaItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val images = loadImages()
            val videos = loadVideos()

            val allMediaItems = mutableListOf<MediaItem>()
            allMediaItems.addAll(images)
            allMediaItems.addAll(videos)

            _mediaItems.value = (allMediaItems)
        }
    }

    private fun loadImages(): List<MediaItem> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        return parseCursor(cursor)
    }

    private fun loadVideos(): List<MediaItem> {
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        )

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = getApplication<Application>().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        return parseCursor(cursor)
    }

    private fun parseCursor(cursor: android.database.Cursor?): List<MediaItem> {
        cursor?.use { c ->
            val idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateAddedColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val bucketIdColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
            val bucketNameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            val mediaItems = mutableListOf<MediaItem>()

            while (c.moveToNext()) {
                val id = c.getLong(idColumn)
                val name = c.getString(nameColumn)
                val dateAdded = c.getLong(dateAddedColumn)
                val bucketId = c.getLong(bucketIdColumn)
                val bucketName = c.getString(bucketNameColumn)

                if (bucketName.startsWith(".")) continue // to remove files under .cache/.thumbnail etc.

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val mediaItem = MediaItem(id, contentUri, name, dateAdded, bucketId, bucketName)
                mediaItems.add(mediaItem)
            }

            return mediaItems
        }
        return emptyList()
    }

    fun getAlbums() : Map<Long, List<MediaItem>> {
        return _mediaItems.value.groupBy {
            it.bucketId
        }
    }

    fun getMediaItemsForAlbum(albumId: Long) : List<MediaItem> {
        return _mediaItems.value.filter {
            it.bucketId == albumId
        }
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }

    fun onClickAlbum(id: Long, mediaItemsList: List<MediaItem>) {
        Log.d("Mariya", "onClick Album")
    }
}