package com.comera.gallery.domain.usecases

import com.comera.gallery.domain.GalleryRepository

class LoadVideosUsecase(val repository: GalleryRepository) {
    suspend operator fun invoke() = repository.loadVideos()
}