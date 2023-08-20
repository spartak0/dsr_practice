package com.example.dsr_practice.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dsr_practice.data.database.converter.DailyConverter

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    val id: Int = 0,
    @ColumnInfo(name = DATE)
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
    @ColumnInfo(name = DAILY)
    val daily: List<DailyEntity>
) {
    companion object {
        const val TABLE_NAME = "weather_table"
        const val ID_COLUMN = "id"
        const val DATE = "date"
        const val NAME_COLUMN = "name"
        const val LAN_COLUMN = "lan"
        const val LON_COLUMN = "lon"
        const val CURRENT_TEMP_COLUMN = "current_temp"
        const val IS_FAVORITE_COLUMN = "is_favorite"
        const val IS_SECOND_DAY_FORECAST_COLUMN = "is_second_day_forecast"
        const val CONDITION = "condition"
        const val CONDITION_ICON = "condition_icon"
        const val DAILY = "daily"
    }
}

data class DailyEntity(
    val dt: Long = 0,
    val morn: Double = 0.0,
    val day: Double = 0.0,
    val eve: Double = 0.0,
    val night: Double = 0.0,
    val windSpeed: Double = 0.0,
    val humidity: Double = 0.0,
    val pressure: Double = 0.0,
)