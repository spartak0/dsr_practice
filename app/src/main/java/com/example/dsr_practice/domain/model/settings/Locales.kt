package com.example.dsr_practice.domain.model.settings

import androidx.annotation.DrawableRes
import com.example.dsr_practice.R

enum class Locales(val tag: String, @DrawableRes val flagId: Int) {
    English(tag = "en", flagId = R.drawable.flag_of_the_united_states),
    Russian(tag = "ru", flagId = R.drawable.flag_of_russia)
}