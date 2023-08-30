package com.example.dsr_practice.ui.edit_trigger_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.R
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
        Log.d("AAA", "updateTrigger: ")
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
    ): VerifyResult = when {
        bindingLocation.isBlank() -> VerifyResult.Error(R.string.your_not_bind_to_location)
        name.isBlank() -> VerifyResult.Error(R.string.incorrect_name)
        (parseStringToDouble(temp) == null)
                && (parseStringToDouble(windSpeed) == null)
                && (parseStringToDouble(humidity) == null)
                && (parseStringToDouble(pressure) == null)
        -> VerifyResult.Error(R.string.fill_in_one_of_the_optional_fields)

        else -> VerifyResult.Success()
    }
}