package com.example.dsr_practice.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String?,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val currentTemp: Double =0.0,
    val isFavorite:Boolean = false,
    val secondDayForecast:Boolean = false,
)