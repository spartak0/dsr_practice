package com.example.dsr_practice.ui.details_settings_screen

enum class ForecastRadioButtonOptions(val route: String, val description: String) {
    OneDay(route="one_day_forecast", description = "without forecast for next day"),
    TwoDay(route="two_day_forecast", description = "with forecast for next day"),
}