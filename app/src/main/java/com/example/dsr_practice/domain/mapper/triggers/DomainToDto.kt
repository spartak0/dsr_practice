package com.example.dsr_practice.domain.mapper.triggers

import com.example.dsr_practice.data.database.triggers.entity.TriggerEntity
import com.example.dsr_practice.domain.model.Trigger

fun Trigger.toEntity() = with(this) {
    TriggerEntity(
        id = id,
        name = name,
        temp = temp,
        windSpeed = windSpeed,
        humidity = humidity,
        pressure = pressure,
        locationId = locationId,
        locationName = locationName,
        units = units?.value
    )
}