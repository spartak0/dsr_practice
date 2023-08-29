package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Place
import com.google.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun fetchPlaces(input: String): Flow<NetworkResult<List<Place>>>
    suspend fun getPlaceByLatLng(latLng: LatLng): Flow<NetworkResult<Place>>
}