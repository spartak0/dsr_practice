package com.example.dsr_practice.ui.choose_binding_locale_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseBindingLocationViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _weather = MutableStateFlow<List<Weather>>(listOf())
    val weather = _weather.asStateFlow()

    init {
        fetchWeather()
    }
    private fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchAllWeather().collect { weatherList ->
                _weather.value = weatherList
            }
        }
    }
}