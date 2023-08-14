package com.example.dsr_practice.ui.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    fun deleteWeather(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteWeatherById(id)
        }
    }
}