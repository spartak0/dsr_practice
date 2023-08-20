package com.example.dsr_practice.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun Long.secToTime(locale: Locale): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("EEE, MMM dd", locale)
    return format.format(date)
}