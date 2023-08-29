package com.example.dsr_practice.ui.details_settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsSettingsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    fun addWeatherInDatabase(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.addWeather(weather)
        }
    }
}