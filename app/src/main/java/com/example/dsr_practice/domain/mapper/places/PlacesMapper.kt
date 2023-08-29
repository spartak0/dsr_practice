package com.example.dsr_practice.domain.mapper.places

import com.example.dsr_practice.data.network.dto.AddressComponent
import com.example.dsr_practice.data.network.dto.GeometryDto
import com.example.dsr_practice.data.network.dto.LocationDto
import com.example.dsr_practice.data.network.dto.PlaceDto
import com.example.dsr_practice.data.network.dto.ResultDto
import com.example.dsr_practice.domain.model.Place
import com.google.maps.model.AddressComponentType
import com.google.maps.model.GeocodingResult

fun PlaceDto.toDomain() = with(this) {
    Place(
        lat = result.geometry.location.lat,
        lon = result.geometry.location.lng,
        address = result.address,
        city = result.addressComponents.first { it.types.contains(AddressComponent.LOCALITY_TYPE) }.shortName
    )
}

fun Place.toDto() = with(this) {
    val location = LocationDto(lat = lat, lng = lon)
    val geometry = GeometryDto(location = location)
    val addressComponents = listOf(AddressComponent(shortName = city, types = listOf("locality")))
    val result = ResultDto(
        address = address,
        geometry = geometry,
        addressComponents = addressComponents
    )
    PlaceDto(result)
}

fun GeocodingResult.toDomain() = with(this) {
    Place(
        lat = geometry.location.lat,
        lon = geometry.location.lng,
        address = formattedAddress,
        city = addressComponents.first { it.types.contains(AddressComponentType.LOCALITY) }.shortName
    )
}