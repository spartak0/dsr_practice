package com.example.dsr_practice.ui.triggers_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.repository.TriggersRepository
import com.example.dsr_practice.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriggersDetailsViewModel @Inject constructor(
    private val triggersRepository: TriggersRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _currentUnits = MutableStateFlow<Units>(Units.Metric)
    val currentUnits = _currentUnits.asStateFlow()

    init {
        fetchCurrentUnits()
    }

    private fun fetchCurrentUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.observeUnit().collectLatest { units ->
                _currentUnits.value = units
            }
        }
    }

    fun deleteTrigger(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.deleteTriggerById(id)
        }
    }
}