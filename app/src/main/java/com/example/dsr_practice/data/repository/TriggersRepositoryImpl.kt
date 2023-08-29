package com.example.dsr_practice.data.repository

import com.example.dsr_practice.data.database.triggers.dao.TriggersDao
import com.example.dsr_practice.data.database.triggers.entity.TriggerEntity
import com.example.dsr_practice.domain.mapper.triggers.toDomain
import com.example.dsr_practice.domain.mapper.triggers.toEntity
import com.example.dsr_practice.domain.model.Trigger
import com.example.dsr_practice.domain.repository.TriggersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TriggersRepositoryImpl(private val triggersDao: TriggersDao) : TriggersRepository {
    override suspend fun fetchTriggers(): Flow<List<Trigger>> =
        triggersDao.fetchTriggersList().map { list ->
            list.map(TriggerEntity::toDomain)
        }

    override suspend fun updateTrigger(trigger: Trigger) =
        triggersDao.updateWeather(trigger.toEntity())

    override suspend fun addTrigger(trigger: Trigger) = triggersDao.addWeather(trigger.toEntity())

    override suspend fun deleteTriggerById(id: Int) = triggersDao.deleteTriggerById(id)
}