//package com.comera.gallery
//
//import android.app.Application
//import android.net.Uri
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.comera.gallery.model.Album
//import com.comera.gallery.model.MediaItem
//import com.comera.gallery.ui.viewmodel.GalleryViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.junit.MockitoJUnit
//import org.mockito.junit.MockitoRule
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class GalleryViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    val mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    private lateinit var observer: Observer<List<Album>>
//
//    private lateinit var galleryViewModel: GalleryViewModel
//
//    @Before
//    fun setup() {
//        val application = ApplicationProvider.getApplicationContext() as Application
//        galleryViewModel = GalleryViewModel(application)
//    }
//
//    @Test
//    fun testLoadingAlbums() = runTest {
//
//    }
//
//    private fun createMockAlbums(): List<Album> {
//        // Create a mock list of albums for testing
//        return listOf(
//            Album("Album 1", listOf(MediaItem(1, Uri.parse("content://image/1"), "IMAGE"))),
//            Album("Album 2", listOf(MediaItem(2, Uri.parse("content://video/1"), "VIDEO")))
//        )
//    }
//}
