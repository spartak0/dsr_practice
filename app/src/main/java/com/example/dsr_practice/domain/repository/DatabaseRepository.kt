package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addWeather(weather: Weather)
    suspend fun fetchWeather(): Flow<List<Weather>>
    suspend fun fetchFavoriteWeather(): Flow<List<Weather>>
    suspend fun deleteWeatherById(id: Int)
    suspend fun updateWeather(weather: Weather)
}