package com.comera.gallery.presentation.viewmodel

import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comera.gallery.domain.MediaItem
import com.comera.gallery.domain.usecases.LoadImagesUsecase
import com.comera.gallery.domain.usecases.LoadVideosUsecase
import com.comera.gallery.domain.usecases.ObserveContentProviderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    val loadImagesUseCase: LoadImagesUsecase,
    val loadVideosUseCase: LoadVideosUsecase,
    private val observeContentProviderUseCase: ObserveContentProviderUseCase
) : ViewModel() {

    private val _albumMap = MutableStateFlow<MutableMap<Long, List<MediaItem>>>(mutableMapOf())

    val albumMap: StateFlow<MutableMap<Long, List<MediaItem>>> get() = _albumMap

    private var allImages = listOf<MediaItem>()
    private var allVideos = listOf<MediaItem>()
    private var _allMediaItems = listOf<MediaItem>()

    val ALL_IMAGES_BUCKET_ID: Long = 923645
    val ALL_VIDEOS_BUCKET_ID: Long = 765643


    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            loadMediaItems()
        }
    }


    init {
        observeContentProviderUseCase.registerContentProvider(contentObserver)
        loadMediaItems()
    }

    fun loadMediaItems() {
        viewModelScope.launch(Dispatchers.IO) {
            allImages = async(Dispatchers.IO) {
                loadImagesUseCase()
            }.await()
            allVideos = async(Dispatchers.IO) {
                loadVideosUseCase()
            }.await()

            val allMediaItems = mutableListOf<MediaItem>()
            if (allImages.isNotEmpty())
                allMediaItems.addAll(allImages)
            if (allVideos.isNotEmpty())
                allMediaItems.addAll(allVideos)

            _allMediaItems = allMediaItems

            _albumMap.value = allMediaItems.groupBy {
                it.bucketId
            }.toMutableMap()
            if (allImages.isNotEmpty())
                _albumMap.value[ALL_IMAGES_BUCKET_ID] = allImages
            if (allVideos.isNotEmpty())
                _albumMap.value[ALL_VIDEOS_BUCKET_ID] = allVideos
        }
    }

    fun getMediaItemsForAlbum(albumId: Long): List<MediaItem>? {
        return _albumMap.value[albumId]
    }


    override fun onCleared() {
        observeContentProviderUseCase.unregisterContentProvider(contentObserver)
        super.onCleared()
    }

    @TestOnly
    fun getAllImages(): List<MediaItem> {
        return allImages
    }

    @TestOnly
    fun getAllVideos(): List<MediaItem> {
        return allVideos
    }

    fun getAllMediaItems(): List<MediaItem> {
        return _allMediaItems
    }
}