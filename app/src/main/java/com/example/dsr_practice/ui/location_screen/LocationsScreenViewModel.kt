package com.example.dsr_practice.ui.location_screen

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
class LocationsScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<List<Weather>>(listOf())
    val weather = _weather.asStateFlow()

    private val _favoriteWeather = MutableStateFlow<List<Weather>>(listOf())
    val favoriteWeather = _favoriteWeather.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchAllWeather()
        fetchFavoriteWeather()
        syncWeather()
    }

    private fun fetchAllWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchAllWeather().collect { weatherList ->
                _weather.value = weatherList
            }
        }
    }

    private fun fetchFavoriteWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchFavoriteWeather().collect { weatherList ->
                _favoriteWeather.value = weatherList
            }
        }
    }


    fun updateWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.updateWeather(weather)
            fetchAllWeather()
        }
    }

    private fun syncWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.syncWeatherData()
        }
    }


    fun onRefresh() {
        syncWeather()
    }
}