package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.Units

interface UserRepository {
    fun setUnit(units: Units)
    fun getUnit(): Units
}