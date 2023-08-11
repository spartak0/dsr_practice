package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getWeatherByCoord(lat: String, lon: String): Flow<NetworkResult<Weather>>
}