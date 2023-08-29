package com.example.dsr_practice.ui.triggers_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.repository.TriggersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriggersDetailsViewModel @Inject constructor(
    private val triggersRepository: TriggersRepository,
) : ViewModel() {
    fun deleteTrigger(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.deleteTriggerById(id)
        }
    }
}