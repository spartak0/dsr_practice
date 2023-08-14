package com.example.dsr_practice.ui.location_name_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationNameViewModel@Inject constructor(): ViewModel() {

    fun validTest(name: String): Boolean = when (name) {
        "" -> false
        else -> true
    }
}