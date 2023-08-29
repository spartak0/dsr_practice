package com.example.dsr_practice.domain.repository

import com.example.dsr_practice.domain.model.Trigger
import kotlinx.coroutines.flow.Flow

interface TriggersRepository {
    suspend fun fetchTriggers(): Flow<List<Trigger>>
    suspend fun updateTrigger(trigger: Trigger)
    suspend fun addTrigger(trigger: Trigger)
    suspend fun deleteTriggerById(id: Int)
}