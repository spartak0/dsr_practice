package com.example.dsr_practice.domain.mapper.weather

import com.example.dsr_practice.data.network.dto.DailyWeatherDto
import com.example.dsr_practice.data.network.dto.WeatherDto
import com.example.dsr_practice.domain.model.DailyWeather
import com.example.dsr_practice.domain.model.Weather


fun WeatherDto.toDomain(): Weather = with(this) {
    Weather(
        id = 0,
        dt = currentWeather.date,
        name = "",
        lat = lat,
        lon = lon,
        currentTemp = currentWeather.temp,
        condition = currentWeather.weatherInfo.component1().condition,
        conditionIcon = currentWeather.weatherInfo.component1().conditionIcon,
        isFavorite = false,
        isSecondDayForecast = false,
        daily = dailyWeather.map(DailyWeatherDto::toDomain),
    )
}

fun DailyWeatherDto.toDomain() = with(this) {
    DailyWeather(
        dt = date,
        morn = temp.morn,
        day = temp.day,
        eve = temp.eve,
        night = temp.night,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure,
    )
}

