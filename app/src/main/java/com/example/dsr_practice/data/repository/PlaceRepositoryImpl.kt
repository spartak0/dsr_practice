package com.example.dsr_practice.data.repository

import androidx.appcompat.app.AppCompatDelegate
import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.domain.mapper.places.toDomain
import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Place
import com.example.dsr_practice.domain.repository.PlaceRepository
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale


class PlaceRepositoryImpl(
    private val api: PlacesApi,
    private val geoApiContext: GeoApiContext,
) : PlaceRepository {

    override suspend fun fetchPlaces(input: String): Flow<NetworkResult<List<Place>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val locales = AppCompatDelegate.getApplicationLocales()
            val currentLocale = locales[0] ?: Locale.ENGLISH
            val response = api.getPredictions(input = input, language = currentLocale.language)
            if (response.isSuccessful) response.body()?.let { predictions ->
                val places = predictions.predictions.map { googlePrediction ->
                    val innerResponse = api.getPlace(
                        placeId = googlePrediction.placeId,
                        language = currentLocale.language
                    )
                    innerResponse.body()?.toDomain() ?: Place()
                }
                emit(NetworkResult.Success(places))
            }
            else emit(NetworkResult.Error(response.message()))
        } catch (t: Throwable) {
            t.message?.let { message ->
                emit(NetworkResult.Error(message))
            }
        }
    }

    override suspend fun getPlaceByLatLng(latLng: LatLng): Flow<NetworkResult<Place>> = flow {
        emit(NetworkResult.Loading())
        try {
            val locales = AppCompatDelegate.getApplicationLocales()
            val currentLocale = locales[0] ?: Locale.ENGLISH
            val results: Array<GeocodingResult> = GeocodingApi.newRequest(geoApiContext)
                .language(currentLocale.language)
                .latlng(latLng)
                .await()
            val place = results.first().toDomain()
            emit(NetworkResult.Success(place))
        } catch (t: Throwable) {
            t.message?.let { message ->
                emit(NetworkResult.Error(message))
            }
        }
    }

}