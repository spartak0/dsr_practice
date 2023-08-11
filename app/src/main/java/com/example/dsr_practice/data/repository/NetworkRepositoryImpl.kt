package com.example.dsr_practice.data.repository

import com.example.dsr_practice.data.network.api.RetrofitApi
import com.example.dsr_practice.domain.mapper.toDomain
import com.example.dsr_practice.domain.model.NetworkResult
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(private val api: RetrofitApi) :
    NetworkRepository {
    override suspend fun getWeatherByCoord(lat: String, lon: String): Flow<NetworkResult<Weather>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val response = api.getWeatherByCoord(lat, lon)
                if (response.isSuccessful) {
                    response.body()?.let { weatherDto ->
                        emit(
                            NetworkResult.Success(weatherDto.toDomain())
                        )
                    }
                } else {
                    emit(NetworkResult.Error(response.message()))
                }
            } catch (t: Throwable) {
                emit(NetworkResult.Error(t.message))
            }
        }
}