package com.example.dsr_practice.data.network.api

import com.example.dsr_practice.data.network.dto.ForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("data/3.0/onecall")
    suspend fun getWeatherDataByCoord(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): Response<ForecastDto>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"

    }
}