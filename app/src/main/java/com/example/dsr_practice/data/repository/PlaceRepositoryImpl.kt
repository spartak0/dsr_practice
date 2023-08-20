package com.example.dsr_practice.data.repository

import android.util.Log
import com.example.dsr_practice.data.network.api.PlacesApi
import com.example.dsr_practice.domain.mapper.toDomain
import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Place
import com.example.dsr_practice.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRepositoryImpl(val api: PlacesApi) : PlaceRepository {
    override suspend fun fetchPlaces(input: String): Flow<NetworkResult<List<Place>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = api.getPredictions(input = input)
            if (response.isSuccessful) response.body()?.let { predictions ->
                Log.d("AAA", "fetchPlaces predictions: $predictions")
                val places = predictions.predictions.map { googlePrediction ->
                    val innerResponse = api.getPlace(placeId = googlePrediction.place_id)
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
}