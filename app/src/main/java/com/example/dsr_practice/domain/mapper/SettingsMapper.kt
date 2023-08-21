package com.example.dsr_practice.domain.mapper

import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units

fun String.toThemeState():ThemeState = when (this) {
    ThemeState.Dark.value -> ThemeState.Dark
    ThemeState.Light.value -> ThemeState.Light
    else -> ThemeState.System
}

fun String.toUnits():Units = when (this) {
    Units.Imperial.title -> Units.Imperial
    else -> Units.Metric
}