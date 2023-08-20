package com.example.dsr_practice.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Weather(
    val id: Int = 0,
    val dt: Long = 0,
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val currentTemp: Double = 0.0,
    val condition: String = "",
    val conditionIcon: String = "",
    val isFavorite: Boolean = false,
    val isSecondDayForecast: Boolean = false,
    val daily: @RawValue List<DailyWeather> = List(DAILY_LIST_SIZE) { DailyWeather() },
) : Parcelable {
    companion object {
        const val DAILY_LIST_SIZE = 8
    }
}

@Parcelize
data class DailyWeather(
    val dt: Long = 0,
    val morn: Double = 0.0,
    val day: Double = 0.0,
    val eve: Double = 0.0,
    val night: Double = 0.0,
    val windSpeed: Double = 0.0,
    val humidity: Double = 0.0,
    val pressure: Double = 0.0,
) : Parcelable
