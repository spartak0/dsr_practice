package com.example.dsr_practice.data.network.dto

data class WeatherDto(
    val lat: Double,
    val lon: Double,
    val current: CurrentWeatherDto,
    val daily: List<DailyWeatherDto>,
)

data class DailyWeatherDto(
    val temp: DailyTempDto,
    val weather: List<WeatherInfoDto>,
)

data class DailyTempDto(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double,
)

data class CurrentWeatherDto(
    val temp: Double,
    val wind_speed: Double,
    val humidity: Double,
    val pressure: Double,
    val weather: List<WeatherInfoDto>
)

data class WeatherInfoDto(val main: String, val icon: String)