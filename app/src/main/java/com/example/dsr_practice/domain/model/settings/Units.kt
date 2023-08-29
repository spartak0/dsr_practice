package com.example.dsr_practice.domain.model.settings

import androidx.annotation.StringRes
import com.example.dsr_practice.R

sealed class Units(
    @StringRes val title: Int,
    val value: String,
    val speedUnit: String,
    val tempUnit: String,
    val pressureUnit: String
) {
    data object Metric :
        Units(
            title = R.string.metric,
            value = "metric",
            speedUnit = "m/s",
            tempUnit = "°C",
            pressureUnit = "hPa"
        )

    data object Imperial :
        Units(
            title = R.string.imperial,
            value = "imperial",
            speedUnit = "mPh",
            tempUnit = "°F",
            pressureUnit = "hPa"
        )
}

