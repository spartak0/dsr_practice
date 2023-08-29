package com.example.dsr_practice.utils

import com.example.dsr_practice.data.network.api.WeatherApi

fun generateIconUrl(url: String) = WeatherApi.PREFIX_URL_ICON + url + WeatherApi.POSTFIX_URL_ICON