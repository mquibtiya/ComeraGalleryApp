package com.comera.gallery.domain.usecases

import android.database.ContentObserver
import com.comera.gallery.domain.GalleryRepository

class ObserveContentProviderUseCase(val repository: GalleryRepository) {

    fun registerContentProvider(contentObserver: ContentObserver) {
        repository.registerMediaContentProvider(contentObserver)
    }

    fun unregisterContentProvider(contentObserver: ContentObserver) {
        repository.unregisterMediaContentProvider(contentObserver)
    }
}