package com.example.dsr_practice.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.secToTime(locale: Locale): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("EEE, MMM dd", locale)
    return format.format(date)
}