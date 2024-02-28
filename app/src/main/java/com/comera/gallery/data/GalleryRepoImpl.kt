package com.comera.gallery.data

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.provider.MediaStore
import com.comera.gallery.domain.GalleryRepository
import com.comera.gallery.domain.MediaItem

class GalleryRepoImpl(val context: Context) : GalleryRepository {

    override suspend fun loadImages(): List<MediaItem> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
//            MediaStore.Video.Media.CONTENT_TYPE,
//            MediaStore.Video.Media.MIME_TYPE,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        return parseCursor(cursor, false)
    }

    override suspend fun loadVideos(): List<MediaItem> {
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
//            MediaStore.Video.Media.CONTENT_TYPE,
//            MediaStore.Video.Media.MIME_TYPE,
        )

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        return parseCursor(cursor, true)
    }

    private fun parseCursor(cursor: android.database.Cursor?, isVideo: Boolean): List<MediaItem> {
        cursor?.use { c ->
            val idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val dateAddedColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val bucketIdColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
            val bucketNameColumn =
                c.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

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
                val mediaItem =
                    MediaItem(id, contentUri, name, dateAdded, bucketId, bucketName, isVideo)
                mediaItems.add(mediaItem)
            }
            return mediaItems
        }
        return emptyList()
    }


    override fun registerMediaContentProvider(contentObserver: ContentObserver) {
        registerImagesContentProvider(contentObserver)
        registerVideosContentProvider(contentObserver)
    }

    override fun unregisterMediaContentProvider(contentObserver: ContentObserver) {
        context.contentResolver.unregisterContentObserver(contentObserver)
    }


    private fun registerImagesContentProvider(contentObserver: ContentObserver) {
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    private fun registerVideosContentProvider(contentObserver: ContentObserver) {
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }
}