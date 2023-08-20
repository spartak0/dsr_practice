package com.example.dsr_practice.data.repository

import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.database.entity.WeatherEntity
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.data.network.dto.DailyWeatherDto
import com.example.dsr_practice.domain.mapper.toDomain
import com.example.dsr_practice.domain.mapper.toEntity
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val api: WeatherApi,
) : WeatherRepository {

    override suspend fun fetchAllWeather(): Flow<List<Weather>> =
        weatherDao.fetchWeatherListFlow().map { list -> list.map(WeatherEntity::toDomain) }

    override suspend fun fetchById(id: Int): Flow<Weather> =
        weatherDao.fetchById(id).map(WeatherEntity::toDomain)


    override suspend fun syncWeatherData() {
        weatherDao.fetchWeatherList().forEach { weatherEntity ->
            try {
                api.getWeatherByCoord(
                    weatherEntity.lat.toString(), weatherEntity.lon.toString()
                ).body()?.let { weatherDto ->
                    val syncWeather = weatherEntity.copy(
                        currentTemp = weatherDto.current.temp,
                        condition = weatherDto.current.weather[0].main,
                        conditionIcon = weatherDto.current.weather[0].icon,
                        daily = weatherDto.daily.map(DailyWeatherDto::toEntity)
                    )
                    weatherDao.updateWeather(syncWeather)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }


    override suspend fun fetchFavoriteWeather(): Flow<List<Weather>> =
        weatherDao.fetchFavoriteWeatherListFlow().map { list -> list.map(WeatherEntity::toDomain) }


    override suspend fun updateWeather(weather: Weather) =
        weatherDao.updateWeather(weather.toEntity())


    override suspend fun addWeather(weather: Weather) = weatherDao.addWeather(weather.toEntity())
    override suspend fun deleteWeatherById(id: Int) = weatherDao.deleteWeatherById(id)
}