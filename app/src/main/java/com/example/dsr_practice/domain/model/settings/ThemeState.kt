package com.example.dsr_practice.domain.model.settings

import com.example.dsr_practice.R

sealed class ThemeState(val route: String, val backgroundId: Int) {
    data object Dark : ThemeState(route = "dark", backgroundId = R.drawable.black_background)
    data object Light : ThemeState(route = "light", backgroundId = R.drawable.white_background)
    data object System : ThemeState(route = "system", backgroundId = R.drawable.black_white_background)
}