package com.comera.gallery.data

import android.content.Context
import com.comera.gallery.domain.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideGalleryRepo(@ApplicationContext context: Context): GalleryRepository =
        GalleryRepoImpl(context)
}