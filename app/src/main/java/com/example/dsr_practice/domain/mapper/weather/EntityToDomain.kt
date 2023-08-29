package com.example.dsr_practice.domain.mapper.weather

import com.example.dsr_practice.data.database.weather.entity.DailyEntity
import com.example.dsr_practice.data.database.weather.entity.WeatherEntity
import com.example.dsr_practice.domain.model.DailyWeather
import com.example.dsr_practice.domain.model.Weather

fun WeatherEntity.toDomain(): Weather = with(this) {
    Weather(
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
        daily = daily.map(DailyEntity::toDomain)
    )
}

fun DailyEntity.toDomain() = with(this) {
    DailyWeather(
        dt = dt,
        morn = morn,
        day = day,
        eve = eve,
        night = night,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure,
    )
}
