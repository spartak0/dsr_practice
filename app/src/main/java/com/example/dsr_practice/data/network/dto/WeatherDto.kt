package com.example.dsr_practice.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName(LAT)
    val lat: Double,
    @SerializedName(LON)
    val lon: Double,
    @SerializedName(CURRENT)
    val currentWeather: CurrentWeatherDto,
    @SerializedName(DAILY)
    val dailyWeather: List<DailyWeatherDto>,
) {
    companion object {
        private const val LAT = "lat"
        private const val LON = "lon"
        private const val CURRENT = "current"
        private const val DAILY = "daily"
    }
}

data class DailyWeatherDto(
    @SerializedName(DATE)
    val date: Long,
    @SerializedName(TEMP)
    val temp: DailyTempDto,
    @SerializedName(WIND_SPEED)
    val windSpeed: Double,
    @SerializedName(HUMIDITY)
    val humidity: Double,
    @SerializedName(PRESSURE)
    val pressure: Double,
) {
    companion object {
        private const val DATE = "dt"
        private const val TEMP = "temp"
        private const val WIND_SPEED = "wind_speed"
        private const val HUMIDITY = "humidity"
        private const val PRESSURE = "pressure"
    }
}

data class DailyTempDto(
    @SerializedName(DAY)
    val day: Double,
    @SerializedName(NIGHT)
    val night: Double,
    @SerializedName(EVE)
    val eve: Double,
    @SerializedName(MORN)
    val morn: Double,
) {
    companion object {
        private const val DAY = "day"
        private const val NIGHT = "night"
        private const val EVE = "eve"
        private const val MORN = "morn"
    }
}

data class CurrentWeatherDto(
    @SerializedName(DATE)
    val date: Long,
    @SerializedName(TEMP)
    val temp: Double,
    @SerializedName(WEATHER)
    val weatherInfo: List<WeatherInfoDto>,
) {
    companion object {
        private const val DATE = "dt"
        private const val TEMP = "temp"
        private const val WEATHER = "weather"
    }
}

data class WeatherInfoDto(
    @SerializedName(MAIN)
    val condition: String,
    @SerializedName(ICON)
    val conditionIcon: String,
) {
    companion object {
        private const val MAIN = "description"
        private const val ICON = "icon"
    }
}