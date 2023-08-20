package com.example.dsr_practice.data.repository

import com.example.dsr_practice.domain.UserSharedPrefHelper
import com.example.dsr_practice.domain.model.Units
import com.example.dsr_practice.domain.repository.UserRepository

class UserRepositoryImpl(private val sharedPrefHelper: UserSharedPrefHelper) : UserRepository {
    override fun setUnit(units: Units) =
        sharedPrefHelper.saveData(UserSharedPrefHelper.UNIT_KEY, units.title)

    override fun getUnit(): Units = when (sharedPrefHelper.loadData(UserSharedPrefHelper.UNIT_KEY)) {
        Units.Imperial.title -> Units.Imperial
        else -> Units.Metric
    }
}