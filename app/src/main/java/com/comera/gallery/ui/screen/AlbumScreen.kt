package com.comera.gallery.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.comera.gallery.model.Album
import com.comera.gallery.model.Albums
import com.comera.gallery.theme.GalleryAppTheme
import com.comera.gallery.ui.viewmodel.GalleryViewModel

class AlbumScreen : ComponentActivity() {
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlbumScreenContent(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreenContent(viewModel: GalleryViewModel = viewModel()) {
    var searchText by remember { mutableStateOf("") }

//    var albums = viewModel.albums.collectAsState().value

    // Filter albums based on search text
    if (searchText.isNotBlank()) {

        /*albums = albums.map { eachAlbum ->
            Album(
                name = eachAlbum.folderNames,
                mediaItems = eachAlbum.mediaItems.filter {
                    it.type.contains(
                        searchText,
                        ignoreCase = true
                    )
                }
            )
        }*/
    }

    Column {
        TopAppBar(
            title = { Text("Gallery App") },
            actions = {
                SearchBar(searchText = searchText) { newText ->
                    searchText = newText
                }
            }
        )

//        AlbumList(albums = albums)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    var searchState by remember { mutableStateOf(searchText) }

    TextField(
        value = searchState,
        onValueChange = {
            searchState = it
            onSearchTextChanged(it)
        },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        placeholder = { Text("Search") },
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun AlbumList(albums: List<Albums>) {
    LazyColumn {
        items(albums) { album ->
            AlbumItem(album = album)
        }
    }
}

@Composable
fun AlbumItem(album: Albums) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Handle click on album
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            album.folderName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Text(text = "${album.imgCount} items")
        }
    }
}


@Composable
fun AlbumScreen(viewModel: GalleryViewModel, onAlbumClick: (Album) -> Unit) {
//    val albums by viewModel.albums

    LazyColumn {
        /*items(albums) { album ->
            AlbumItem(album = album, onAlbumClick = onAlbumClick)
        }*/
    }
}