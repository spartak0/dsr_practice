package com.example.dsr_practice.data.database.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dsr_practice.data.database.weather.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME}")
    fun fetchWeatherList(): List<WeatherEntity>

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME}")
    fun fetchWeatherListFlow(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.IS_FAVORITE_COLUMN} = 1")
    fun fetchFavoriteWeatherListFlow(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID_COLUMN} = :id")
    fun fetchById(id: Int): Flow<WeatherEntity>

    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID_COLUMN} = :id")
    fun deleteWeatherById(id: Int)

    @Insert
    fun addWeather(weatherEntity: WeatherEntity)

    @Update
    fun updateWeather(weatherEntity: WeatherEntity)
}