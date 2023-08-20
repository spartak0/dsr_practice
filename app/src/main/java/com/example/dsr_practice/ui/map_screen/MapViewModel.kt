package com.example.dsr_practice.ui.map_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Place
import com.example.dsr_practice.domain.repository.PlaceRepository
import com.example.dsr_practice.utils.DefaultLatLng
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(val placesRepository: PlaceRepository) : ViewModel() {
    private val _markerState = MutableStateFlow(MarkerState(DefaultLatLng.value))
    val markerState = _markerState.asStateFlow()

    private val _autocompletePlaces =
        MutableStateFlow<NetworkResult<List<Place>>>(NetworkResult.Loading())
    val autocompletePlaces = _autocompletePlaces.asStateFlow()


    @SuppressLint("MissingPermission")
    fun getDeviceLocation(context: Context) {
        viewModelScope.launch {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val lastKnownLocation = task.result
                    _markerState.value.position =
                        LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                }
            }
        }
    }

    fun setMarker(latLng: LatLng) {
        _markerState.value.position = latLng
    }

    fun fetchPlaces(place: String) {
        viewModelScope.launch {
            placesRepository.fetchPlaces(place).collect {
                _autocompletePlaces.value = it
            }
        }
    }

}