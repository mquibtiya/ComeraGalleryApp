package com.comera.gallery.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comera.gallery.domain.MediaItem
import com.comera.gallery.domain.usecases.LoadImagesUsecase
import com.comera.gallery.domain.usecases.LoadVideosUsecase
import com.comera.gallery.domain.usecases.ObserveContentProviderUseCase
import com.comera.gallery.presentation.viewmodel.GalleryViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GalleryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var galleryViewModel: GalleryViewModel

    @Mock
    private var loadImagesUseCase: LoadImagesUsecase = mock(LoadImagesUsecase::class.java)

    @Mock
    private var loadVideosUseCase: LoadVideosUsecase = mock(LoadVideosUsecase::class.java)

    @Mock
    private var observeContentProviderUseCase: ObserveContentProviderUseCase =
        mock(ObserveContentProviderUseCase::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Initialize ViewModel with mocked use cases
        galleryViewModel =
            GalleryViewModel(loadImagesUseCase, loadVideosUseCase, observeContentProviderUseCase)
    }


    @Test
    fun `test loadMediaItems`() = runBlocking {
        // Given
        val fakeImages = listOf(mock(MediaItem::class.java))
        val fakeVideos = listOf(mock(MediaItem::class.java))

        // Stub the use cases
        `when`(loadImagesUseCase.invoke()).thenReturn(fakeImages)
        `when`(loadVideosUseCase.invoke()).thenReturn(fakeVideos)

        // When
        galleryViewModel.loadMediaItems()

        delay(2000)

        // Then
        assertEquals(fakeImages, galleryViewModel.getAllImages())
        assertEquals(fakeVideos, galleryViewModel.getAllVideos())
        assertEquals(fakeImages + fakeVideos, galleryViewModel.getAllMediaItems())
    }


    /*@Test
    fun `test getMediaItemsForAlbum`() = runBlocking {
        // Given
        val fakeMediaItems = listOf<MediaItem>(mock(MediaItem::class.java))
        val fakeAlbumId = fakeMediaItems[0].bucketId

        // Mock the album map
        val mockAlbumMap = mutableMapOf<Long, List<MediaItem>>()
        `when`(mockAlbumMap[fakeAlbumId]).thenReturn(fakeMediaItems)

        // When
        val result = galleryViewModel.getMediaItemsForAlbum(fakeAlbumId)

        delay(1000)

        // Then
        assertEquals(fakeMediaItems, result)
    }*/
}