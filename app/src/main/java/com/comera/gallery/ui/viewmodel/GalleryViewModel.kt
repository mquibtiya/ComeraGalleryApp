package com.comera.gallery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comera.gallery.domain.MediaItem
import com.comera.gallery.domain.usecases.LoadImagesUsecase
import com.comera.gallery.domain.usecases.LoadVideosUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val loadImagesUsecase: LoadImagesUsecase,
    val loadVideosUsecase: LoadVideosUsecase,
) : ViewModel() {

    //    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    private val _albumMap = MutableStateFlow<MutableMap<Long, List<MediaItem>>>(mutableMapOf())

    //    val mediaItems: StateFlow<List<MediaItem>> get() = _mediaItems
    val albumMap: StateFlow<MutableMap<Long, List<MediaItem>>> get() = _albumMap

    private var allImages = listOf<MediaItem>()
    private var allVideos = listOf<MediaItem>()

    val ALL_IMAGES_BUCKET_ID: Long = 923645
    val ALL_VIDEOS_BUCKET_ID: Long = 765643

    init {
        loadMediaItems()
        /*context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )*/
    }

    fun loadMediaItems() {
        viewModelScope.launch(Dispatchers.IO) {
            allImages = loadImagesUsecase()
            allVideos = loadVideosUsecase()

            val allMediaItems = mutableListOf<MediaItem>()
            allMediaItems.addAll(allImages)
            allMediaItems.addAll(allVideos)

//            _mediaItems.value = (allMediaItems)

            _albumMap.value = allMediaItems.groupBy {
                it.bucketId
            }.toMutableMap()
            if (allImages.isNotEmpty())
                _albumMap.value[ALL_IMAGES_BUCKET_ID] = allImages
            if (allVideos.isNotEmpty())
                _albumMap.value[ALL_VIDEOS_BUCKET_ID] = allVideos
        }
    }

    /*fun getAlbums(): Map<Long, List<MediaItem>> {
        val albumMap = _mediaItems.value.groupBy {
            it.bucketId
        }.toMutableMap()
        if (allImages.isNotEmpty())
            albumMap[ALL_IMAGES_BUCKET_ID] = allImages
        if (allVideos.isNotEmpty())
            albumMap[ALL_VIDEOS_BUCKET_ID] = allVideos
        return albumMap
    }*/

    fun getMediaItemsForAlbum(albumId: Long): List<MediaItem>? {
        return _albumMap.value[albumId]
        /*if (albumId == ALL_IMAGES_BUCKET_ID)
            return allImages
        return if (albumId == ALL_VIDEOS_BUCKET_ID)
            allVideos
        else
            _mediaItems.value.filter {
                it.bucketId == albumId
            }*/
    }

    /*fun onClickAlbum(id: Long, mediaItemsList: List<MediaItem>) {
        Log.d("Mariya", "onClick Album")
//        navController.navigate(Routes.Signup.route)
    }*/
}