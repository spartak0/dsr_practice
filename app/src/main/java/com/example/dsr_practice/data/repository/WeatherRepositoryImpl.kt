package com.example.dsr_practice.data.repository

import androidx.appcompat.app.AppCompatDelegate
import com.example.dsr_practice.data.database.weather.dao.WeatherDao
import com.example.dsr_practice.data.database.weather.entity.WeatherEntity
import com.example.dsr_practice.data.network.api.WeatherApi
import com.example.dsr_practice.data.network.dto.DailyWeatherDto
import com.example.dsr_practice.domain.UserPrefHelper
import com.example.dsr_practice.domain.mapper.weather.toDomain
import com.example.dsr_practice.domain.mapper.weather.toEntity
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class WeatherRepositoryImpl(
    private val weatherDao: WeatherDao,
    private val api: WeatherApi,
    private val prefHelper: UserPrefHelper,
) : WeatherRepository {

    override suspend fun fetchAllWeather(): Flow<List<Weather>> =
        weatherDao.fetchWeatherListFlow().map { list -> list.map(WeatherEntity::toDomain) }

    override suspend fun fetchById(id: Int): Flow<Weather> =
        weatherDao.fetchById(id).map(WeatherEntity::toDomain)


    override suspend fun syncWeatherData() {
        weatherDao.fetchWeatherList().forEach { weatherEntity ->
            try {
                val unit = prefHelper.observeUnit().first().value
                val locales = AppCompatDelegate.getApplicationLocales()
                val locale = locales[0] ?: Locale.ENGLISH
                api.getWeatherByCoord(
                    lat = weatherEntity.lat.toString(),
                    lon = weatherEntity.lon.toString(),
                    units = unit,
                    language = locale.language
                ).body()?.let { weatherDto ->
                    val syncWeather = weatherEntity.copy(
                        currentTemp = weatherDto.currentWeather.temp,
                        condition = weatherDto.currentWeather.weatherInfo.component1().condition,
                        conditionIcon = weatherDto.currentWeather.weatherInfo.component1().conditionIcon,
                        daily = weatherDto.dailyWeather.map(DailyWeatherDto::toEntity)
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
    override suspend fun forecastById(id: Int): Flow<Weather> = flow {
        try {
            val weather = weatherDao.fetchById(id).map(WeatherEntity::toDomain).first()
            val response = api.getWeatherByCoord(weather.lat.toString(), weather.lon.toString())
            if (response.isSuccessful) {
                response.body()?.let { dto ->
                    emit(dto.toDomain())
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }

    }
}