package com.example.dsr_practice.data.network.api

import com.example.dsr_practice.data.network.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(FORECAST_ENDPOINT)
    suspend fun getWeatherByCoord(
        @Query(LAT_PARAMETER) lat: String?,
        @Query(LON_PARAMETER) lon: String?,
        @Query(APP_ID_PARAMETER) appId: String = API_KEY,
        @Query(UNITS_PARAMETER) units: String = UNITS_DEFAULT_VALUE,
        @Query(EXCLUDE_PARAMETER) exclude: String = EXClUDE_DEFAULT_VALUE,
        @Query(LANGUAGE_PARAMETER) language: String = LANGUAGE_DEFAULT_VALUE,
    ): Response<WeatherDto>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "f84d512e108bb1c37e3d87e758f31045"
        const val PREFIX_URL_ICON = "https://openweathermap.org/img/wn/"
        const val POSTFIX_URL_ICON = "@4x.png"
        private const val FORECAST_ENDPOINT = "data/2.5/onecall"
        private const val UNITS_DEFAULT_VALUE = "metric"
        private const val EXClUDE_DEFAULT_VALUE = "minutely,hourly,alerts"
        private const val LANGUAGE_DEFAULT_VALUE = "ru"
        private const val LAT_PARAMETER = "lat"
        private const val LON_PARAMETER = "lon"
        private const val APP_ID_PARAMETER = "appid"
        private const val UNITS_PARAMETER = "units"
        private const val EXCLUDE_PARAMETER = "exclude"
        private const val LANGUAGE_PARAMETER = "lang"
    }
}