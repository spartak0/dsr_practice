package com.example.dsr_practice.data.network.dto

data class ForecastDto(val lat: Double, val lon: Double, val current: CurrentDto)
data class CurrentDto(val temp: Double, val weather: WeatherDto)
data class WeatherDto(val main: String, val icon: String)