package com.comera.gallery.ui.viewmodel

import android.net.Uri
import com.comera.gallery.domain.usecases.LoadImagesUsecase
import com.comera.gallery.domain.usecases.LoadVideosUsecase
import com.comera.gallery.domain.usecases.MockGalleryRepo
import com.comera.gallery.domain.usecases.ObserveContentProviderUseCase
import com.comera.gallery.presentation.viewmodel.GalleryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GalleryViewModelTest {

    private lateinit var loadImagesUsecase: LoadImagesUsecase
    private lateinit var loadVideosUsecase: LoadVideosUsecase
    private lateinit var observeContentProviderUseCase: ObserveContentProviderUseCase
    private lateinit var galleryViewModel: GalleryViewModel

    @Before
    fun setUp() {

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk()

        // Initialize ViewModel with mocked use cases
        val mockGalleryRepo = MockGalleryRepo()
        loadImagesUsecase = LoadImagesUsecase(mockGalleryRepo)
        loadVideosUsecase = LoadVideosUsecase(mockGalleryRepo)
        observeContentProviderUseCase = ObserveContentProviderUseCase(mockGalleryRepo)

        galleryViewModel =
            GalleryViewModel(loadImagesUsecase, loadVideosUsecase, observeContentProviderUseCase)
    }


    @Test
    fun `test loadMediaItems`() = runBlocking {
        // Given
        galleryViewModel.loadMediaItems()

        delay(600)

        val imageList = galleryViewModel.getAllImages()
        assertEquals(imageList.size, 3)

        val videoList = galleryViewModel.getAllVideos()
        assertEquals(videoList.size, 3)

        assertEquals(galleryViewModel.albumMap.value.size, 5) // All Images and All Videos so +2
    }


    @Test
    fun `test getMediaItemsForAlbum`() = runBlocking {
        // Given
        galleryViewModel.loadMediaItems()

        delay(600)

        val fakeMediaItems = galleryViewModel.getAllMediaItems()
        val fakeAlbumId = fakeMediaItems[0].bucketId

        val albumMap = fakeMediaItems.filter { it.bucketId == fakeAlbumId }

        // When
        val result = galleryViewModel.getMediaItemsForAlbum(fakeAlbumId)

        // Then
        assertEquals(albumMap, result)
        assertEquals(result?.size, 2)
    }
}