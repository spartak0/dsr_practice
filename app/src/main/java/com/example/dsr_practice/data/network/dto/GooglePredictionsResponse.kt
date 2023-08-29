package com.example.dsr_practice.data.network.dto

import com.google.gson.annotations.SerializedName

data class GooglePredictionsResponse(
    val predictions: ArrayList<GooglePrediction>
)

data class GooglePrediction(
    @SerializedName(DESCRIPTION)
    val description: String,
    @SerializedName(PLACE_ID)
    val placeId: String,
) {
    companion object {
        private const val DESCRIPTION = "description"
        private const val PLACE_ID = "place_id"
    }
}