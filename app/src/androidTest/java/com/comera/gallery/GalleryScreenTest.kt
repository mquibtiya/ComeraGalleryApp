package com.comera.gallery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.comera.gallery.domain.MediaItem
import com.comera.gallery.presentation.ui.composable.GalleryScreen
import com.comera.gallery.presentation.viewmodel.GalleryViewModel
import io.mockk.every
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class GalleryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testGalleryScreenUI() {
        // Mock data
        val albums = mapOf<Long, List<MediaItem>>(
            1L to listOf(mock(MediaItem::class.java)),
            2L to listOf(mock(MediaItem::class.java))
            // Add more albums as needed
        )

        // Launch the GalleryScreen composable
        composeTestRule.setContent {
            GalleryScreen(
                navController = mockNavController(),
                viewModel = createMockViewModel(albums)
            )
        }

        // Check if the app bar is displayed
        composeTestRule.onNodeWithText("Gallery").assertIsDisplayed()

        // Check if the AlbumItemCard components are displayed
        /*albums.keys.forEach { albumId ->
            val albumName = albums.get(albumId)?.get(0)?.bucketName
            onView(withText("$albumName")).check(matches(isDisplayed()))
            // Add more assertions as needed
        }*/
    }

    private fun mockNavController(): NavHostController {
        val navController = mock(NavHostController::class.java)
        every { navController.navigate(any<String>()) } returns Unit
        return navController
    }

    private fun createMockViewModel(albums: Map<Long, List<MediaItem>>): GalleryViewModel {
        val viewModel = mock(GalleryViewModel::class.java)
        every { viewModel.albumMap } returns MutableStateFlow(albums.toMutableMap())
        return viewModel
    }
}