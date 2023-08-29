package com.example.dsr_practice.ui.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _useDarkTheme = MutableStateFlow<ThemeState>(ThemeState.System)
    val useDarkTheme = _useDarkTheme.asStateFlow()

    init {
        fetchThemeSettings()
    }

    private fun fetchThemeSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.observeThemeState().collectLatest { themeState ->
                _useDarkTheme.value = themeState
            }
        }
    }
}