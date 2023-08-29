package com.example.dsr_practice.domain.mapper.weather

import com.example.dsr_practice.data.network.dto.CurrentWeatherDto
import com.example.dsr_practice.data.network.dto.DailyTempDto
import com.example.dsr_practice.data.network.dto.DailyWeatherDto
import com.example.dsr_practice.data.network.dto.WeatherDto
import com.example.dsr_practice.data.network.dto.WeatherInfoDto
import com.example.dsr_practice.domain.model.DailyWeather
import com.example.dsr_practice.domain.model.Weather

fun Weather.toDto(): WeatherDto = with(this) {
    val weatherInfo = WeatherInfoDto(condition, conditionIcon)
    val currentTemp = CurrentWeatherDto(dt, currentTemp, listOf(weatherInfo))
    val daily = daily.map(DailyWeather::toDto)
    return WeatherDto(lat, lon, currentTemp, daily)
}

fun DailyWeather.toDto() = with(this) {
    DailyWeatherDto(
        date = dt,
        temp = DailyTempDto(
            day = day,
            night = night,
            morn = morn,
            eve = eve,
        ),
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure,
    )
}