package com.example.dsr_practice.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val id: Int = 0,
    val name: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val currentTemp: Double =0.0,
    val isFavorite:Boolean = false,
    val secondDayForecast:Boolean = false,
) : Parcelable
