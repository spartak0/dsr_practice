package com.example.dsr_practice.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.database.entity.WeatherEntity


@Database(entities = [WeatherEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}