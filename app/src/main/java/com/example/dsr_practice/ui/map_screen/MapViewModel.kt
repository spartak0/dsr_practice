package com.example.dsr_practice.ui.map_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MapViewModel @Inject constructor() : ViewModel() {
    private val _markerState = MutableStateFlow(MarkerState(DefaultLatLng.value))
    val markerState = _markerState.asStateFlow()

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
}