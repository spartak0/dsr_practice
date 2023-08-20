package com.example.dsr_practice.domain

import android.content.Context
import android.content.SharedPreferences

class UserSharedPrefHelper(val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveData(key: String, value: String?) {
        editor.putString(key, value).apply()
    }

    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    companion object {
        const val USER_SHARED_PREF = "userSharedPref"
        const val UNIT_KEY = "unit"
    }
}