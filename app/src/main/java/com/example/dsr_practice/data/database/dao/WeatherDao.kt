package com.example.dsr_practice.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dsr_practice.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME}")
    fun fetchWeatherList(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.IS_FAVORITE_COLUMN}=1")
    fun fetchFavoriteWeatherList(): Flow<List<WeatherEntity>>

    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID_COLUMN} = :id")
    fun deleteWeatherById(id: Int)

    @Insert
    fun addWeather(weatherEntity: WeatherEntity)

    @Update
    fun updateWeather(weatherEntity: WeatherEntity)
}