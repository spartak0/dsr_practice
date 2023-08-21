package com.example.dsr_practice.domain.model.settings

sealed class Units(
    val title: String,
    val speedUnit: String,
    val tempUnit: String,
    val pressureUnit: String
) {
    data object Metric :
        Units(title = "Metric", speedUnit = "m/s", tempUnit = "°C", pressureUnit = "hPa")

    data object Imperial :
        Units(title = "Imperial", speedUnit = "mPh", tempUnit = "°F", pressureUnit = "hPa")
}

