package com.example.dsr_practice.ui.triggers_screen

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.repository.TriggersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(private val triggersRepository: TriggersRepository) :
    ViewModel() {
    private val _triggers = MutableStateFlow<List<Trigger>>(listOf())
    val triggers = _triggers.asStateFlow()

    init {
        fetchTriggers()
    }

    private fun fetchTriggers() {
        viewModelScope.launch {
            triggersRepository.fetchTriggers().collect {
                _triggers.value = it
            }
        }
    }

    fun checkNotificationsEnabled(context: Context) =
        NotificationManagerCompat.from(context).areNotificationsEnabled()

}