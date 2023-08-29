package com.example.dsr_practice.domain.mapper.weather

import com.example.dsr_practice.data.database.weather.entity.DailyEntity
import com.example.dsr_practice.data.database.weather.entity.WeatherEntity
import com.example.dsr_practice.domain.model.DailyWeather
import com.example.dsr_practice.domain.model.Weather

fun Weather.toEntity(): WeatherEntity = with(this) {
    WeatherEntity(
        id = id,
        dt = dt,
        name = name,
        lat = lat,
        lon = lon,
        currentTemp = currentTemp,
        isFavorite = isFavorite,
        isSecondDayForecast = isSecondDayForecast,
        condition = condition,
        conditionIcon = conditionIcon,
        daily = daily.map(DailyWeather::toEntity)
    )
}

fun DailyWeather.toEntity() = with(this) {
    DailyEntity(
        dt = dt,
        morn = morn,
        day = day,
        eve = day,
        night = night,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure,
    )
}