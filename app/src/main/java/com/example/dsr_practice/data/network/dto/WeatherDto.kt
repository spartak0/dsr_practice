package com.example.dsr_practice.data.network.dto

data class WeatherDto(val lat: Double, val lon: Double, val current: CurrentWeatherDto)
data class CurrentWeatherDto(
    val temp: Double,
    val wind_speed: Double,
    val humidity:Double,
    val pressure:Double,
    val weather: Array<WeatherInfoDto>
)

data class WeatherInfoDto(val main: String, val icon: String)