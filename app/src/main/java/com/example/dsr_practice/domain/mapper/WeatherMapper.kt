package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.database.entity.DailyEntity
import com.example.dsr_practice.data.network.dto.CurrentWeatherDto
import com.example.dsr_practice.data.network.dto.DailyTempDto
import com.example.dsr_practice.data.network.dto.DailyWeatherDto
import com.example.dsr_practice.data.network.dto.WeatherDto
import com.example.dsr_practice.data.network.dto.WeatherInfoDto
import com.example.dsr_practice.domain.model.DailyWeather
import com.example.dsr_practice.domain.model.Weather

fun Weather.toDto(): WeatherDto = with(this) {
    val weatherInfo = WeatherInfoDto(condition, conditionIcon)
    val currentTemp = CurrentWeatherDto(dt, currentTemp, listOf(weatherInfo))
    val daily = daily.map(DailyWeather::toDto)
    return WeatherDto(lat, lon, currentTemp, daily)
}

fun WeatherDto.toDomain(): Weather = with(this) {
    Weather(
        id = 0,
        dt = current.dt,
        name = "",
        lat = lat,
        lon = lon,
        currentTemp = current.temp,
        condition = current.weather[0].main,
        conditionIcon = current.weather[0].icon,
        isFavorite = false,
        isSecondDayForecast = false,
        daily = daily.map(DailyWeatherDto::toDomain),
    )
}

fun DailyWeather.toDto() = with(this) {
    DailyWeatherDto(
        dt = dt,
        temp = DailyTempDto(
            day = day,
            night = night,
            morn = morn,
            eve = eve,
        ),
        wind_speed = windSpeed,
        humidity = humidity,
        pressure = pressure,
    )
}

fun DailyWeatherDto.toDomain() = with(this) {
    DailyWeather(
        dt = dt,
        morn = temp.morn,
        day = temp.day,
        eve = temp.eve,
        night = temp.night,
        windSpeed = wind_speed,
        humidity = humidity,
        pressure = pressure,
    )
}

fun DailyWeatherDto.toEntity() = with(this) {
    DailyEntity(
        dt = dt,
        morn = temp.morn,
        day = temp.day,
        eve = temp.eve,
        night = temp.night,
        windSpeed = wind_speed,
        humidity = humidity,
        pressure = pressure,
    )
}