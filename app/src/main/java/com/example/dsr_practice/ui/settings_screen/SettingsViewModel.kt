package com.example.dsr_practice.ui.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Units
import com.example.dsr_practice.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _currentUnits = MutableStateFlow<Units>(Units.Imperial)
    val currentUnits = _currentUnits.asStateFlow()

    init {
        fetchCurrentUnits()
    }

    fun setUnits(units: Units) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setUnit(units)
        }
    }

    fun fetchCurrentUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentUnits.value = userRepository.getUnit()
        }
    }


}