package com.example.dsr_practice.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.UserPrefHelper
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val userPrefHelper: UserPrefHelper,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    private val _rootGraphStartDestination = MutableStateFlow<String?>("")
    val rootGraphStartDestination = _rootGraphStartDestination.asStateFlow()

    private val _weather = MutableStateFlow<Weather?>(Weather())
    val weather = _weather.asStateFlow()

    init {
        fetchWeather()
        fetchStartDestination()
    }
    private fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefHelper.observeWeatherId().collect {
                it?.let {
                    _weather.value = weatherRepository.fetchById(it).first()
                }
            }
        }
    }

    private fun fetchStartDestination() {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefHelper.observeStartRoute().collect {
                _rootGraphStartDestination.value = it
            }
        }
    }

    fun clearStartDestination() {
        viewModelScope.launch(Dispatchers.IO) {
            userPrefHelper.setStartRoute("")
        }
    }
}