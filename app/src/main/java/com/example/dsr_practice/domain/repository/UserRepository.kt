package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun setUnit(units: Units)
    fun observeUnit(): Flow<Units>
    fun observeThemeState(): Flow<ThemeState>
    suspend fun setThemeSettings(themeState: ThemeState)
}