package com.example.dsr_practice.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dsr_practice.domain.mapper.toThemeState
import com.example.dsr_practice.domain.mapper.toUnits
import com.example.dsr_practice.domain.model.settings.ThemeState
import com.example.dsr_practice.domain.model.settings.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val USER_SHARED_PREF = "userSharedPref"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SHARED_PREF)

class UserPrefHelper(val context: Context) {
    private suspend fun saveData(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    private fun getData(): Flow<Preferences> = context.dataStore.data

    suspend fun setUnit(units: Units) = saveData(UNIT_KEY, units.title)

    fun observeUnit(): Flow<Units> = getData().map { prefs ->
        prefs[UNIT_KEY]?.toUnits() ?: Units.Metric
    }

    suspend fun setThemeSettings(themeState: ThemeState) =
        saveData(THEME_KEY, themeState.value)


    fun observeThemeSettings(): Flow<ThemeState> = getData().map { prefs ->
        prefs[THEME_KEY]?.toThemeState() ?: ThemeState.System
    }

    companion object {
        val UNIT_KEY = stringPreferencesKey("unit")
        val THEME_KEY = stringPreferencesKey("theme")
    }
}