package com.example.dsr_practice.data.database.weather.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dsr_practice.data.database.weather.converter.DailyConverter

@Entity(tableName = WeatherEntity.TABLE_NAME)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int = 0,
    @ColumnInfo(name = DATE_COLUMN)
    val dt: Long,
    @ColumnInfo(name = NAME_COLUMN)
    val name: String = "",
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
    @ColumnInfo(name = CONDITION)
    val condition: String = "",
    @ColumnInfo(name = CONDITION_ICON)
    val conditionIcon: String = "",
    @TypeConverters(DailyConverter::class)
    @ColumnInfo(name = DAILY_COLUMN)
    val daily: List<DailyEntity>
) {
    companion object {
        const val TABLE_NAME = "weather_table"
        const val ID_COLUMN = "id"
        const val DATE_COLUMN = "date"
        const val NAME_COLUMN = "name"
        const val LAN_COLUMN = "lan"
        const val LON_COLUMN = "lon"
        const val CURRENT_TEMP_COLUMN = "current_temp"
        const val IS_FAVORITE_COLUMN = "is_favorite"
        const val IS_SECOND_DAY_FORECAST_COLUMN = "is_second_day_forecast"
        const val CONDITION = "condition"
        const val CONDITION_ICON = "condition_icon"
        const val DAILY_COLUMN = "daily"
    }
}

data class DailyEntity(
    @ColumnInfo(name = DAILY_DATE_COLUMN)
    val dt: Long = 0,
    @ColumnInfo(name = DAILY_MORN_COLUMN)
    val morn: Double = 0.0,
    @ColumnInfo(name = DAILY_DAY_COLUMN)
    val day: Double = 0.0,
    @ColumnInfo(name = DAILY_EVE_COLUMN)
    val eve: Double = 0.0,
    @ColumnInfo(name = DAILY_NIGHT_COLUMN)
    val night: Double = 0.0,
    @ColumnInfo(name = DAILY_WIND_SPEED_COLUMN)
    val windSpeed: Double = 0.0,
    @ColumnInfo(name = DAILY_HUMIDITY_COLUMN)
    val humidity: Double = 0.0,
    @ColumnInfo(name = DAILY_PRESSURE_COLUMN)
    val pressure: Double = 0.0,
) {
    companion object {
        const val DAILY_DATE_COLUMN = "daily_date"
        const val DAILY_MORN_COLUMN = "daily_morn"
        const val DAILY_DAY_COLUMN = "daily_day"
        const val DAILY_EVE_COLUMN = "daily_eve"
        const val DAILY_NIGHT_COLUMN = "daily_night"
        const val DAILY_WIND_SPEED_COLUMN = "daily_wind_speed"
        const val DAILY_HUMIDITY_COLUMN = "daily_humidity"
        const val DAILY_PRESSURE_COLUMN = "daily_pressure"
    }
}