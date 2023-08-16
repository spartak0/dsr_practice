package com.example.dsr_practice.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dsr_practice.data.database.converter.DailyConverter
import com.example.dsr_practice.data.database.dao.WeatherDao
import com.example.dsr_practice.data.database.entity.WeatherEntity


@TypeConverters(DailyConverter::class)
@Database(
    entities = [WeatherEntity::class],
    version = WeatherDatabase.DATABASE_VERSION,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "weather_database"
        const val DATABASE_VERSION = 5
    }
}