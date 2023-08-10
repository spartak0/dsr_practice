package com.example.dsr_practice.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = ID_COLUMN)
    val id: Int = 0,
    @ColumnInfo(name = NAME_COLUMN)
    val name: String?,
    @ColumnInfo(name = LAN_COLUMN)
    val lat: Double = 0.0,
    @ColumnInfo(name = LON_COLUMN)
    val lon: Double = 0.0,
    @ColumnInfo(name = CURRENT_TEMP_COLUMN)
    val currentTemp: Double = 0.0,
    @ColumnInfo(name = IS_FAVORITE_COLUMN)
    val isFavorite: Boolean = false,
    @ColumnInfo(name = IS_SECOND_DAY_FORECAST_COLUMN)
    val isSecondDayForecast: Boolean = false,
) {
    companion object {
        const val TABLE_NAME = "weather_table"
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val LAN_COLUMN = "lan"
        const val LON_COLUMN = "lon"
        const val CURRENT_TEMP_COLUMN = "current_temp"
        const val IS_FAVORITE_COLUMN = "is_favorite"
        const val IS_SECOND_DAY_FORECAST_COLUMN = "is_second_day_forecast"
    }
}