package com.example.dsr_practice.domain.mapper.settings

import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import java.util.Locale

fun String.toThemeState():ThemeState = when (this) {
    ThemeState.Dark.route -> ThemeState.Dark
    ThemeState.Light.route -> ThemeState.Light
    else -> ThemeState.System
}

fun String.toUnits():Units = when (this) {
    Units.Imperial.value -> Units.Imperial
    else -> Units.Metric
}

fun String.toLocale(): Locale = Locale(this)