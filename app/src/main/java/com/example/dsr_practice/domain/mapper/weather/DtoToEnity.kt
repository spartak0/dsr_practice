package com.example.dsr_practice.domain.mapper.weather

import com.example.dsr_practice.data.database.weather.entity.DailyEntity
import com.example.dsr_practice.data.network.dto.DailyWeatherDto

fun DailyWeatherDto.toEntity() = with(this) {
    DailyEntity(
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