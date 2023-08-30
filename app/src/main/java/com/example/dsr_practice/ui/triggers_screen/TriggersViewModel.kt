package com.example.dsr_practice.ui.triggers_screen

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.repository.TriggersRepository
import com.example.dsr_practice.utils.NetworkConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(
    private val triggersRepository: TriggersRepository,
    private val networkConnectionManager: NetworkConnectionManager,
) :
    ViewModel() {
    private val _triggers = MutableStateFlow<List<Trigger>>(listOf())
    val triggers = _triggers.asStateFlow()

    private val _networkConnection = MutableStateFlow(true)
    val networkConnection = _networkConnection.asStateFlow()

    private val _notifyAvailable = MutableStateFlow(true)
    val notifyAvailable = _notifyAvailable.asStateFlow()

    init {
        fetchTriggers()
        fetchNetworkConnection()
        networkConnectionManager.startListenNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        networkConnectionManager.stopListenNetworkState()
    }

    private fun fetchTriggers() {
        viewModelScope.launch(Dispatchers.IO) {
            triggersRepository.fetchTriggers().collect {
                _triggers.value = it
            }
        }
    }

    private fun fetchNetworkConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            networkConnectionManager.isNetworkConnectedFlow.collect {
                _networkConnection.value = it
            }
        }
    }

    fun fetchNotifyAvailable(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _notifyAvailable.value = checkNotificationsEnabled(context)
        }
    }

    private fun checkNotificationsEnabled(context: Context) =
        NotificationManagerCompat.from(context).areNotificationsEnabled()

}