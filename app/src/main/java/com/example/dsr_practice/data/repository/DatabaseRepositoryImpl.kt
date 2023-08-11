package com.example.dsr_practice.data.repository

import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.database.entity.WeatherEntity
import com.example.dsr_practice.domain.mapper.toDomain
import com.example.dsr_practice.domain.mapper.toEntity
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseRepositoryImpl(
    private val weatherDao: WeatherDao,
) :
    DatabaseRepository {
    override suspend fun addWeather(weather: Weather) = weatherDao.addWeather(weather.toEntity())

    override suspend fun fetchWeather(): Flow<List<Weather>> =
        weatherDao.fetchWeatherList().map { list -> list.map(WeatherEntity::toDomain) }

    override suspend fun fetchFavoriteWeather(): Flow<List<Weather>> =
        weatherDao.fetchFavoriteWeatherList().map { list -> list.map(WeatherEntity::toDomain) }

    override suspend fun deleteWeatherById(id: Int) = weatherDao.deleteWeatherById(id)
    override suspend fun updateWeather(weather: Weather) =
        weatherDao.updateWeather(weather.toEntity())
}