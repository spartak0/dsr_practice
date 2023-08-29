package com.example.dsr_practice.ui.edit_trigger_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.repository.TriggersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTriggersViewModel @Inject constructor(
    private val triggersRepository: TriggersRepository
) : ViewModel() {
    fun addTrigger(trigger: Trigger) {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.addTrigger(trigger)
        }
    }

    fun updateTrigger(trigger: Trigger) {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.updateTrigger(trigger)
        }
    }

    fun deleteTrigger(triggerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.deleteTriggerById(triggerId)
        }
    }

    fun parseStringToDouble(str: String): Double? = str.replace(",", ".").toDoubleOrNull()

    fun verify(
        bindingLocation: String,
        name: String,
        temp: String,
        windSpeed: String,
        humidity: String,
        pressure: String
    ): Boolean = when {
        bindingLocation.isBlank() -> false
        name.isBlank() -> false
        (parseStringToDouble(temp) == null) && (parseStringToDouble(windSpeed) == null) && (parseStringToDouble(
            humidity
        ) == null) && (parseStringToDouble(pressure) == null) -> false

        else -> true
    }
}