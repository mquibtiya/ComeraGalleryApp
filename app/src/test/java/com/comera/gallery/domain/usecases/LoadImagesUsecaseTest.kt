package com.comera.gallery.domain.usecases

import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoadImagesUsecaseTest {

    private lateinit var loadImagesUsecase: LoadImagesUsecase
    private lateinit var mockGalleryRepo: MockGalleryRepo

    @Before
    fun setUp() {

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk()

        mockGalleryRepo = MockGalleryRepo()
        loadImagesUsecase = LoadImagesUsecase(mockGalleryRepo)
    }

    @Test
    fun `Get Image List, correct image list return` (): Unit = runBlocking{
        val mediaItem = loadImagesUsecase().first()
        assertEquals(mediaItem.displayName, "name1")

    }

    @Test
    fun `Get Image List, incorrect image list return` (): Unit = runBlocking{
        val mediaItem = loadImagesUsecase().last()
        assertNotEquals(mediaItem.displayName, "name1")
    }

    @After
    fun tearDown() {
    }

    @Test
    operator fun invoke() = runBlocking{
        val mediaItems = loadImagesUsecase()
        assertEquals(mediaItems.size, 3)
    }

    @Test
    fun getRepository() {
        assertEquals(loadImagesUsecase.repository, mockGalleryRepo)
    }
}