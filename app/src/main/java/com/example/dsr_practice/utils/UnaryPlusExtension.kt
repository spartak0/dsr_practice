package com.example.dsr_practice.utils

fun Int.toTempString(): String = if (this > 0) "+$this" else this.toString()
fun Double.toTempString(): String {
    val value = if (this % 1 == 0.0) this.toInt().toString() else this.toString()
    return if (this > 0) "+$value" else value
}

fun Double.toIntString(): String = if (this % 1 == 0.0) this.toInt().toString() else this.toString()