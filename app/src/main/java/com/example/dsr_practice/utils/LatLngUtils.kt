package com.example.dsr_practice.utils

import com.google.android.gms.maps.model.LatLng

fun LatLng.isDefault(): Boolean {
    return this.latitude == 0.0 && this.longitude == 0.0
}

object DefaultLatLng {
    val value: LatLng = LatLng(0.0, 0.0)
}