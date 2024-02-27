package com.comera.gallery.data

import com.comera.gallery.domain.GalleryRepository
import com.comera.gallery.domain.usecases.LoadImagesUsecase
import com.comera.gallery.domain.usecases.LoadVideosUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    fun provideLoadImageUsecase(repository: GalleryRepository): LoadImagesUsecase =
        LoadImagesUsecase(repository)

    @Provides
    fun provideLoadVideoUsecase(repository: GalleryRepository): LoadVideosUsecase =
        LoadVideosUsecase(repository)
}