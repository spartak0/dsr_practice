package com.example.dsr_practice.ui.location_screen.view_pager.all_tab

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Weather
import com.example.dsr_practice.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTabViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _weather = MutableStateFlow<List<Weather>>(listOf())
    val weather = _weather.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        fetchAllWeather()
    }

    private fun fetchAllWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.fetchWeather().collect { weatherList ->
                _weather.value = weatherList
            }
        }
    }

    fun updateWeather(weather: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.updateWeather(weather)
        }
    }

    fun onRefresh() {
        fetchAllWeather()
        Log.d("AAA", "onRefresh")
    }
}