package com.example.dsr_practice.ui.map_screen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dsr_practice.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun MapScreen(navigator: DestinationsNavigator) {
    val moscow = LatLng(55.751244, 37.618423)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(moscow, 10f)
    }
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    MapContent(
        searchText = text,
        searchTextChange = { text = it },
        onSearch = {
            active = false
            focusManager.clearFocus()
        },
        activeSearchBar = false,
        onActiveChange = { active = it },
        cameraPositionState = cameraPositionState,
        permissionsDismiss = { navigator.navigateUp() },
        floatingActionBtnOnClick = {},
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapContent(
    searchText: String,
    searchTextChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    activeSearchBar: Boolean,
    onActiveChange: (Boolean) -> Unit,
    cameraPositionState: CameraPositionState,
    permissionsDismiss: () -> Unit,
    floatingActionBtnOnClick: () -> Unit,
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = floatingActionBtnOnClick) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_my_location_24),
                contentDescription = null
            )
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                query = searchText,
                onQueryChange = searchTextChange,
                onSearch = onSearch,
                active = activeSearchBar,
                onActiveChange = onActiveChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false
                )
            )

        }
    }
    MapPermissions(onDismiss = permissionsDismiss)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapPermissions(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val permissions = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    ) {
        if (it[Manifest.permission.ACCESS_COARSE_LOCATION] == false) {
            onDismiss()
            Toast.makeText(
                context,
                "Turn on tracking.\nYou can do it in settings.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    SideEffect {
        permissions.launchMultiplePermissionRequest()
    }

}
