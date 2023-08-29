package com.example.dsr_practice.data.network.dto

import com.google.gson.annotations.SerializedName

data class PlaceDto(val result: ResultDto)
data class ResultDto(
    @SerializedName("formatted_address")
    val address: String,
    @SerializedName("address_components")
    val addressComponents: List<AddressComponent>,
    @SerializedName("geometry")
    val geometry: GeometryDto,
)

data class GeometryDto(val location: LocationDto)
data class LocationDto(val lat: Double, val lng: Double)
data class AddressComponent(
    @SerializedName(SHORT_NAME)
    val shortName: String,
    @SerializedName(TYPES)
    val types: List<String>,
){
    companion object{
        private const val SHORT_NAME = "short_name"
        private const val TYPES = "types"
        const val LOCALITY_TYPE = "locality"
    }
}

