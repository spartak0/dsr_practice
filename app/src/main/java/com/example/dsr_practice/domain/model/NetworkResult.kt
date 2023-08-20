package com.example.dsr_practice.domain.model

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String, data:T? = null): NetworkResult<T>(data = data, message = message)
    class Loading<T>(data: T? = null): NetworkResult<T>(data = data)
}
