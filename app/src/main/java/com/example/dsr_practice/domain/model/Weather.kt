package com.example.dsr_practice.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val id: Int = 0,
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val currentTemp: Double = 0.0,
    val condition: String = "",
    val conditionIcon: String = "",
    val isFavorite: Boolean = false,
    val isSecondDayForecast: Boolean = false,
    val windSpeed: Double = 0.0,
    val humidity:Double = 0.0,
    val pressure:Double= 0.0,
) : Parcelable
