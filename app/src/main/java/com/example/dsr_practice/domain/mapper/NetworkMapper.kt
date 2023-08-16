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
    val currentTemp =
        CurrentWeatherDto(currentTemp, windSpeed, humidity, pressure, listOf(weatherInfo))
    val daily = daily.map(DailyWeather::toDto)
    return WeatherDto(lat, lon, currentTemp, daily)
}

fun WeatherDto.toDomain(): Weather = with(this) {
    Weather(
        id = 0,
        name = "",
        lat = lat,
        lon = lon,
        currentTemp = current.temp,
        condition = current.weather[0].main,
        conditionIcon = current.weather[0].icon,
        isFavorite = false,
        isSecondDayForecast = false,
        windSpeed = current.wind_speed,
        humidity = current.humidity,
        pressure = current.pressure,
        daily = daily.map(DailyWeatherDto::toDomain),
    )
}

fun DailyWeather.toDto() = with(this) {
    DailyWeatherDto(
        temp = DailyTempDto(
            day = day,
            night = night,
            morn = morn,
            eve = eve,
        ),
        weather = listOf(WeatherInfoDto(main = condition, icon = conditionIcon))
    )
}

fun DailyWeatherDto.toDomain() = with(this) {
    DailyWeather(
        morn = temp.morn,
        day = temp.day,
        eve = temp.eve,
        night = temp.night,
        condition = weather[0].main,
        conditionIcon = weather[0].icon,
    )
}

fun DailyWeatherDto.toEntity() = with(this) {
    DailyEntity(
        morn = temp.morn,
        day = temp.day,
        eve = temp.eve,
        night = temp.night,
        condition = weather[0].main,
        conditionIcon = weather[0].icon,
    )
}