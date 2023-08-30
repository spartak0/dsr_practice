package com.example.dsr_practice.ui.settings_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.repository.UserRepository
import com.example.dsr_practice.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) :
    ViewModel() {

    private val _currentUnits = MutableStateFlow<Units>(Units.Metric)
    val currentUnits = _currentUnits.asStateFlow()

    private val _useDarkTheme = MutableStateFlow<ThemeState>(ThemeState.System)
    val useDarkTheme = _useDarkTheme.asStateFlow()


    init {
        fetchThemeState()
        fetchCurrentUnits()
    }

    fun setUnits(units: Units) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setUnit(units)
        }
    }

    private fun fetchCurrentUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.observeUnit().collectLatest { units ->
                _currentUnits.value = units
            }
        }
    }

    fun setThemeSettings(themeState: ThemeState) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setThemeSettings(themeState)
        }
    }

    private fun fetchThemeState() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.observeThemeState().collectLatest { themeState ->
                _useDarkTheme.value = themeState
            }
        }
    }

    fun isInternetAvailable(context: Context): Boolean =
        NetworkHelper.isInternetAvailable(context)

}