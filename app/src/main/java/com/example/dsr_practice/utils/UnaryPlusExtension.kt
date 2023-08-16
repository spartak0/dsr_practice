package com.example.dsr_practice.utils

fun Int.checkUnaryPlus(): String = if (this > 0) "+$this" else this.toString()