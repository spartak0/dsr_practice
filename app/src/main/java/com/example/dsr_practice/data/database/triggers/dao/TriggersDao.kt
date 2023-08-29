package com.example.dsr_practice.data.database.triggers.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dsr_practice.data.database.triggers.entity.TriggerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TriggersDao {

    @Query("SELECT * FROM ${TriggerEntity.TABLE_NAME}")
    fun fetchTriggersList(): Flow<List<TriggerEntity>>

    @Query("DELETE FROM ${TriggerEntity.TABLE_NAME} WHERE ${TriggerEntity.ID_COLUMN} = :id")
    fun deleteTriggerById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeather(triggerEntity: TriggerEntity)

    @Update
    fun updateWeather(triggerEntity: TriggerEntity)
}