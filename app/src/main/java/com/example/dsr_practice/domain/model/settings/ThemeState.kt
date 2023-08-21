package com.example.dsr_practice.domain.model.settings

import com.example.dsr_practice.R

sealed class ThemeState(val value: String, val backgroundId: Int) {
    data object Dark : ThemeState(value = "dark", backgroundId = R.drawable.black_background)
    data object Light : ThemeState(value = "light", backgroundId = R.drawable.white_background)
    data object System : ThemeState(value = "system", backgroundId = R.drawable.black_white_background)
}