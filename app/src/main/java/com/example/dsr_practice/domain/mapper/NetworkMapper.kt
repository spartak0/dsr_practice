package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.network.dto.CurrentWeatherDto
import com.example.dsr_practice.data.network.dto.WeatherDto
import com.example.dsr_practice.data.network.dto.WeatherInfoDto
import com.example.dsr_practice.domain.model.Weather

fun Weather.toDto(): WeatherDto = with(this) {
    val weatherInfo = WeatherInfoDto(condition, conditionIcon)
    val currentTemp =
        CurrentWeatherDto(currentTemp, windSpeed, humidity, pressure, arrayOf(weatherInfo))
    return WeatherDto(lat, lon, currentTemp)
}

fun WeatherDto.toDomain(): Weather = with(this) {
    Weather(
        id = 0,
        name = "",
        lat = lat,
        lon = lon,
        currentTemp = current.temp,
        condition = current.weather[0].main,
        conditionIcon = current.weather[0].icon,
        isFavorite = false,
        isSecondDayForecast = false,
        windSpeed = current.wind_speed,
        humidity = current.humidity,
        pressure = current.pressure,
    )
}