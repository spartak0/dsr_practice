package com.example.dsr_practice.ui.details_settings_screen

import androidx.annotation.StringRes
import com.example.dsr_practice.R

enum class ForecastRadioButtonOptions(val route: String, @StringRes val description: Int) {
    OneDay(route = "one_day_forecast", description = R.string.without_forecast_for_next_day),
    TwoDay(route = "two_day_forecast", description = R.string.with_forecast_for_next_day),
}