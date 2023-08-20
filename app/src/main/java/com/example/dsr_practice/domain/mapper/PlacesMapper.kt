package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.data.network.dto.GeometryDto
import com.example.dsr_practice.data.network.dto.LocationDto
import com.example.dsr_practice.data.network.dto.PlaceDto
import com.example.dsr_practice.data.network.dto.ResultDto
import com.example.dsr_practice.domain.model.Place

fun PlaceDto.toDomain() = with(this) {
    Place(
        lat = result.geometry.location.lat,
        lon = result.geometry.location.lng,
        address = result.formatted_address
    )
}

fun Place.toDto() = with(this) {
    val location = LocationDto(lat = lat, lng = lon)
    val geometry = GeometryDto(location = location)
    val result = ResultDto(
        formatted_address = address,
        geometry = geometry
    )
    PlaceDto(result)
}