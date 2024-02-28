package com.comera.gallery.domain.usecases

import com.comera.gallery.domain.GalleryRepository

class LoadImagesUsecase(val repository: GalleryRepository) {
    suspend operator fun invoke() = repository.loadImages()
}