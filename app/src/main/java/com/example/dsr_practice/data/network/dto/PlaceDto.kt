package com.example.dsr_practice.data.network.dto

data class PlaceDto(val result: ResultDto)
data class ResultDto(val formatted_address: String, val geometry: GeometryDto)
data class GeometryDto(val location: LocationDto)
data class LocationDto(val lat: Double, val lng: Double)