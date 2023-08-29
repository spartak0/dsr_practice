package com.example.dsr_practice.data.repository

import com.example.dsr_practice.domain.UserPrefHelper
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import com.example.dsr_practice.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val prefHelper: UserPrefHelper,
) : UserRepository {

    override suspend fun setUnit(units: Units) =
        prefHelper.setUnit(units)

    override fun observeUnit(): Flow<Units> = prefHelper.observeUnit()

    override suspend fun setThemeSettings(themeState: ThemeState) =
        prefHelper.setThemeSettings(themeState)


    override fun observeThemeState(): Flow<ThemeState> = prefHelper.observeThemeSettings()
}