package com.example.dsr_practice.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dsr_practice.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    suspend fun getLocalePicture(): Flow<List<WeatherEntity>>
}