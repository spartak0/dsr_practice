package com.example.dsr_practice.domain.mapper.settings

import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units

fun String.toThemeState():ThemeState = when (this) {
    ThemeState.Dark.route -> ThemeState.Dark
    ThemeState.Light.route -> ThemeState.Light
    else -> ThemeState.System
}

fun String.toUnits():Units = when (this) {
    Units.Imperial.value -> Units.Imperial
    else -> Units.Metric
}

fun String?.toUnitsOrNull():Units? = when (this) {
    Units.Imperial.value -> Units.Imperial
    Units.Metric.value -> Units.Metric
    else -> null
}