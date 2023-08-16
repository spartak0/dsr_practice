package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.database.entity.DailyEntity
import com.example.dsr_practice.data.database.entity.WeatherEntity
import com.example.dsr_practice.domain.model.DailyWeather
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
        pressure = pressure,
        daily = daily.map(DailyWeather::toEntity)
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
        pressure = pressure,
        daily = daily.map(DailyEntity::toDomain)
    )
}

fun DailyEntity.toDomain() = with(this) {
    DailyWeather(
        morn = morn,
        day = day,
        eve = eve,
        night = night,
        condition = condition,
        conditionIcon = conditionIcon
    )
}

fun DailyWeather.toEntity() = with(this) {
    DailyEntity(
        morn = morn,
        day = day,
        eve = day,
        night = night,
        condition = condition,
        conditionIcon = conditionIcon,
    )
}