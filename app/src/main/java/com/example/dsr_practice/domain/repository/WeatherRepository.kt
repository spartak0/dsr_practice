package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchFavoriteWeather(): Flow<List<Weather>>
    suspend fun fetchAllWeather(): Flow<List<Weather>>
    suspend fun fetchById(id: Int): Flow<Weather>
    suspend fun syncWeatherData()
    suspend fun updateWeather(weather: Weather)
    suspend fun addWeather(weather: Weather)
    suspend fun deleteWeatherById(id: Int)
}