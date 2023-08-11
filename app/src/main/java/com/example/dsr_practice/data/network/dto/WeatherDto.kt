package com.example.dsr_practice.data.network.dto

data class WeatherDto(val lat: Double, val lon: Double, val current: CurrentTempDto)
data class CurrentTempDto(val temp: Double, val weather: WeatherInfo)
data class WeatherInfo(val main: String, val icon: String)