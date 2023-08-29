package com.example.dsr_practice.ui.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.repository.UserRepository
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _currentUnits = MutableStateFlow<Units>(Units.Metric)
    val currentUnits = _currentUnits.asStateFlow()

    init {
        fetchCurrentUnits()
    }

    fun deleteWeather(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.deleteWeatherById(id)
        }
    }

    private fun fetchCurrentUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.observeUnit().collectLatest { units ->
                _currentUnits.value = units
            }
        }
    }
}