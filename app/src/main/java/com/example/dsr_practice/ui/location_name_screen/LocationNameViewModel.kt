package com.example.dsr_practice.ui.location_name_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.repository.PlaceRepository
import com.google.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationNameViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun fetchPlaceName(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            placeRepository.getPlaceByLatLng(latLng).collect { place ->
                _name.value = place.data?.city ?: ""
            }
        }
    }

    fun setName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _name.value = name
        }
    }

    fun validTest(name: String): Boolean = name.isNotBlank()
}