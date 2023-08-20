package com.example.dsr_practice.data.network.api

import com.example.dsr_practice.data.network.dto.GooglePredictionsResponse
import com.example.dsr_practice.data.network.dto.PlaceDto
import com.example.dsr_practice.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = Constants.MAPS_API_KEY,
        @Query("types") types: String = TYPES,
        @Query("input") input: String
    ): Response<GooglePredictionsResponse>

    @GET("maps/api/place/details/json")
    suspend fun getPlace(
        @Query("placeid") placeId: String,
        @Query("key") key: String = Constants.MAPS_API_KEY,
    ): Response<PlaceDto>

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
        const val TYPES = "address"
    }
}