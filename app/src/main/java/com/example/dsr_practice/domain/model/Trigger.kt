package com.example.dsr_practice.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trigger(
    val id: Int = 0,
    val name: String? = null,
    val temp: Double? = null,
    val windSpeed: Double? = null,
    val humidity: Double? = null,
    val pressure: Double? = null,
    val locationId: Int = 0,
    val locationName: String? = null,
) : Parcelable