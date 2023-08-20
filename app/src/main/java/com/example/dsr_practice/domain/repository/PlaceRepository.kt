package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun fetchPlaces(input: String): Flow<NetworkResult<List<Place>>>
}