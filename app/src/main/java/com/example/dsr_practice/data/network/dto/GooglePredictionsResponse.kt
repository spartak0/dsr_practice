package com.example.dsr_practice.data.network.dto

data class GooglePredictionsResponse(
    val predictions: ArrayList<GooglePrediction>
)
data class GooglePrediction(
    val description: String,
    val place_id:String,
)