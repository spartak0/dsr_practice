package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.network.dto.CurrentTempDto
import com.example.dsr_practice.data.network.dto.WeatherDto
import com.example.dsr_practice.data.network.dto.WeatherInfo
import com.example.dsr_practice.domain.model.Weather

fun Weather.toDto(): WeatherDto = with(this) {
    val weatherInfo = WeatherInfo(condition, "")
    val currentTemp = CurrentTempDto(currentTemp, weatherInfo)
    return WeatherDto(lat, lon, currentTemp)
}

fun WeatherDto.toDomain(): Weather = with(this) {
    Weather(
        id = 0,
        name = "",
        lat = lat,
        lon = lon,
        currentTemp = current.temp,
        condition = current.weather.main,
        conditionIcon = current.weather.icon,
        isFavorite = false,
        isSecondDayForecast = false,
    )
}