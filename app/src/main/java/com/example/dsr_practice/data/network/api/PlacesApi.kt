package com.example.dsr_practice.data.network.api

import com.example.dsr_practice.data.network.dto.GooglePredictionsResponse
import com.example.dsr_practice.data.network.dto.PlaceDto
import com.example.dsr_practice.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {
    @GET(PREDICTIONS_ENDPOINT)
    suspend fun getPredictions(
        @Query(KEY_PARAMETER) key: String = Constants.MAPS_API_KEY,
        @Query(TYPE_PARAMETER) types: String = TYPE_DEFAULT_VALUE,
        @Query(INPUT_PARAMETER) input: String,
        @Query(LANGUAGE_PARAMETER) language: String = LANGUAGE_DEFAULT_VALUE
    ): Response<GooglePredictionsResponse>

    @GET(PLACE_DETAILS_ENDPOINT)
    suspend fun getPlace(
        @Query(PLACE_ID_PARAMETER) placeId: String,
        @Query(KEY_PARAMETER) key: String = Constants.MAPS_API_KEY,
        @Query(LANGUAGE_PARAMETER) language: String = LANGUAGE_DEFAULT_VALUE
    ): Response<PlaceDto>

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
        private const val TYPE_DEFAULT_VALUE = "address"
        private const val AUTOCOMPLETE_API = "maps/api/place"
        private const val PREDICTIONS_ENDPOINT = "$AUTOCOMPLETE_API/autocomplete/json"
        private const val PLACE_DETAILS_ENDPOINT = "$AUTOCOMPLETE_API/details/json"
        private const val KEY_PARAMETER = "key"
        private const val TYPE_PARAMETER = "types"
        private const val INPUT_PARAMETER = "input"
        private const val LANGUAGE_PARAMETER = "language"
        private const val PLACE_ID_PARAMETER = "placeid"
        private const val LANGUAGE_DEFAULT_VALUE = "en"
    }
}