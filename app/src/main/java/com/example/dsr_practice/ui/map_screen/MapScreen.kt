package com.example.dsr_practice.ui.map_screen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.model.Place
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.ui.composables.SearchBar
import com.example.dsr_practice.ui.destinations.LocationNameScreenDestination
import com.example.dsr_practice.utils.Constants
import com.example.dsr_practice.utils.DefaultLatLng
import com.example.dsr_practice.utils.isDefault
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Destination
@Composable
fun MapScreen(navigator: DestinationsNavigator, viewModel: MapViewModel = hiltViewModel()) {
    val moscow = LatLng(Constants.MOSCOW_LATITUDE, Constants.MOSCOW_LONGITUDE)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(moscow, 10f)
    }
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val markerState by viewModel.markerState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val autocompletePlaces by viewModel.autocompletePlaces.collectAsState()

    MapContent(
        searchText = text,
        searchTextChange = {
            text = it
            viewModel.fetchPlaces(it)
        },
        cameraPositionState = cameraPositionState,
        permissionsDismiss = { navigator.navigateUp() },
        currentLocationOnClick = {
            scope.launch {
                viewModel.getDeviceLocation(context)
                delay(100)
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        markerState.position,
                        10f
                    )
                )
            }
        },
        markerState = markerState,
        onMapClick = { latLng ->
            markerState.position = latLng
            focusManager.clearFocus()
        },
        onNextClick = {
            navigator.navigate(
                LocationNameScreenDestination(
                    weatherData = Weather(
                        lat = markerState.position.latitude,
                        lon = markerState.position.longitude,
                    )
                )
            )
        },
        autocompletePlaces = autocompletePlaces.data ?: listOf(),
        placeOnClick = { place ->
            val newLatLng = LatLng(place.lat, place.lon)
            viewModel.setMarker(newLatLng)
            scope.launch {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        newLatLng,
                        10f
                    )
                )
            }
            focusManager.clearFocus()
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapContent(
    searchText: String,
    searchTextChange: (String) -> Unit,
    autocompletePlaces: List<Place>,
    placeOnClick: (Place) -> Unit,
    cameraPositionState: CameraPositionState,
    permissionsDismiss: () -> Unit,
    currentLocationOnClick: () -> Unit,
    markerState: MarkerState,
    onMapClick: (LatLng) -> Unit,
    onNextClick: () -> Unit,
) {
    val markerVisibility = !markerState.position.isDefault()

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            onMapClick = onMapClick
        ) {
            Marker(state = markerState, visible = markerVisibility, onClick = {
                markerState.position = DefaultLatLng.value
                true
            })

        }
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = searchText,
            onValueChange = searchTextChange,
            autocompleteList = autocompletePlaces,
            itemOnClick = placeOnClick,
        )
        FloatingActionButton(
            onClick = currentLocationOnClick,
            modifier = Modifier
                .padding(bottom = 16.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.my_location_icon),
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = markerVisibility, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = onNextClick,
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
            }
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
                context.getText(R.string.toast_turn_on_tracking),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    SideEffect {
        permissions.launchMultiplePermissionRequest()
    }

}
