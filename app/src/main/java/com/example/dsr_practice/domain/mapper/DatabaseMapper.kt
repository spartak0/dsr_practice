package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.database.entity.WeatherEntity
import com.example.dsr_practice.domain.model.Weather

fun Weather.toEntity(): WeatherEntity = with(this) {
    WeatherEntity(
        id = id,
        name = name,
        lat = lat,
        lon = lon,
        currentTemp = currentTemp,
        isFavorite = isFavorite,
        isSecondDayForecast = isSecondDayForecast,
        condition = condition,
        conditionIcon = conditionIcon,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure

    )
}

fun WeatherEntity.toDomain(): Weather = with(this) {
    Weather(
        id = id,
        name = name,
        lat = lat,
        lon = lon,
        currentTemp = currentTemp,
        isFavorite = isFavorite,
        isSecondDayForecast = isSecondDayForecast,
        condition = condition,
        conditionIcon = conditionIcon,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure
    )
}