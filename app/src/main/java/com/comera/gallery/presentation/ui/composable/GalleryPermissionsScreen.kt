package com.comera.gallery.presentation.ui.composable

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.comera.gallery.nav.Routes

@Composable
fun GalleryPermissionsScreen(navController: NavController) {

    val permissionsToRequest: Array<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }


    var permissionsGranted by remember {
        mutableStateOf(false)
    }

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            var allPermissions = true
            for (key: String in it.keys) {
                if (it[key] == false) {
                    allPermissions = false
                    break
                }
            }
            permissionsGranted = allPermissions
        }
    )

    if (permissionsGranted) {
        navController.navigate(Routes.AlbumView.screen) {
            popUpTo(0)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                multiplePermissionResultLauncher.launch(permissionsToRequest)
            }) {
                Text(text = "Request Permission")
            }
        }
        SideEffect {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }
    }
}